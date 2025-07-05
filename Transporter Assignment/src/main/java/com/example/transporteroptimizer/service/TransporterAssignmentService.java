package com.example.transporteroptimizer.service;

import com.example.transporteroptimizer.model.Lane;
import com.example.transporteroptimizer.model.Transporter;
import com.example.transporteroptimizer.model.LaneQuote;
import com.example.transporteroptimizer.repository.LaneRepository;
import com.example.transporteroptimizer.repository.TransporterRepository;
import com.example.transporteroptimizer.repository.LaneQuoteRepository;
import com.example.transporteroptimizer.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransporterAssignmentService {
    private final LaneRepository laneRepository;
    private final TransporterRepository transporterRepository;
    private final LaneQuoteRepository laneQuoteRepository;

    public TransporterAssignmentService(LaneRepository laneRepository, TransporterRepository transporterRepository, LaneQuoteRepository laneQuoteRepository) {
        this.laneRepository = laneRepository;
        this.transporterRepository = transporterRepository;
        this.laneQuoteRepository = laneQuoteRepository;
    }

    @Transactional
    public void storeInput(List<LaneDTO> laneDTOs, List<TransporterDTO> transporterDTOs) {
        laneQuoteRepository.deleteAll();
        transporterRepository.deleteAll();
        laneRepository.deleteAll();

        List<Lane> lanes = laneDTOs.stream()
                .map(dto -> new Lane(dto.getId(), dto.getOrigin(), dto.getDestination()))
                .collect(Collectors.toList());
        laneRepository.saveAll(lanes);

        for (TransporterDTO transporterDTO : transporterDTOs) {
            Transporter transporter = new Transporter();
            transporter.setId(transporterDTO.getId());
            transporter.setName(transporterDTO.getName());
            transporterRepository.save(transporter);
            for (LaneQuoteDTO quoteDTO : transporterDTO.getLaneQuotes()) {
                Lane lane = laneRepository.findById(quoteDTO.getLaneId()).orElse(null);
                if (lane != null) {
                    LaneQuote laneQuote = new LaneQuote();
                    laneQuote.setLane(lane);
                    laneQuote.setTransporter(transporter);
                    laneQuote.setQuote(quoteDTO.getQuote());
                    laneQuoteRepository.save(laneQuote);
                }
            }
        }
    }

    public AssignmentResponseDTO assignTransporters(int maxTransporters) {
        List<Lane> lanes = laneRepository.findAll();
        List<Transporter> allTransporters = transporterRepository.findAll();
        List<LaneQuote> allQuotes = laneQuoteRepository.findAll();

        // Map: laneId -> (transporterId -> quote)
        Map<Long, List<LaneQuote>> laneToQuotes = allQuotes.stream().collect(Collectors.groupingBy(lq -> lq.getLane().getId()));
        Map<Long, Integer> transporterUsage = new java.util.HashMap<>();
        java.util.Set<Long> selectedTransporters = new java.util.HashSet<>();
        List<com.example.transporteroptimizer.dto.AssignmentDTO> assignments = new java.util.ArrayList<>();
        int totalCost = 0;

        for (Lane lane : lanes) {
            List<LaneQuote> quotes = laneToQuotes.getOrDefault(lane.getId(), java.util.Collections.emptyList());
            LaneQuote bestQuote = null;
            for (LaneQuote quote : quotes) {
                if (selectedTransporters.size() < maxTransporters || selectedTransporters.contains(quote.getTransporter().getId())) {
                    if (bestQuote == null || quote.getQuote() < bestQuote.getQuote()) {
                        bestQuote = quote;
                    }
                }
            }
            if (bestQuote != null) {
                com.example.transporteroptimizer.dto.AssignmentDTO assignment = new com.example.transporteroptimizer.dto.AssignmentDTO();
                assignment.setLaneId(lane.getId());
                assignment.setTransporterId(bestQuote.getTransporter().getId());
                assignments.add(assignment);
                totalCost += bestQuote.getQuote();
                selectedTransporters.add(bestQuote.getTransporter().getId());
                transporterUsage.put(bestQuote.getTransporter().getId(), transporterUsage.getOrDefault(bestQuote.getTransporter().getId(), 0) + 1);
            }
        }

        AssignmentResponseDTO response = new AssignmentResponseDTO();
        response.setStatus("success");
        response.setTotalCost(totalCost);
        response.setAssignments(assignments);
        response.setSelectedTransporters(new java.util.ArrayList<>(selectedTransporters));
        return response;
    }
} 