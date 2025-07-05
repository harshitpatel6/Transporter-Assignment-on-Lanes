package com.example.transporteroptimizer.service;

import com.example.transporteroptimizer.dto.*;
import com.example.transporteroptimizer.model.Lane;
import com.example.transporteroptimizer.model.Transporter;
import com.example.transporteroptimizer.model.LaneQuote;
import com.example.transporteroptimizer.repository.LaneRepository;
import com.example.transporteroptimizer.repository.TransporterRepository;
import com.example.transporteroptimizer.repository.LaneQuoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TransporterAssignmentService.class)
public class TransporterAssignmentServiceTest {
    @Autowired
    private LaneRepository laneRepository;
    @Autowired
    private TransporterRepository transporterRepository;
    @Autowired
    private LaneQuoteRepository laneQuoteRepository;
    @Autowired
    private TransporterAssignmentService service;

    @BeforeEach
    void setUp() {
        laneQuoteRepository.deleteAll();
        transporterRepository.deleteAll();
        laneRepository.deleteAll();
    }

    @Test
    void testAssignmentOptimization() {
        LaneDTO lane1 = new LaneDTO(); lane1.setId(1L); lane1.setOrigin("Mumbai"); lane1.setDestination("Delhi");
        LaneDTO lane2 = new LaneDTO(); lane2.setId(2L); lane2.setOrigin("Chennai"); lane2.setDestination("Kolkata");
        TransporterDTO t1 = new TransporterDTO(); t1.setId(1L); t1.setName("T1");
        TransporterDTO t2 = new TransporterDTO(); t2.setId(2L); t2.setName("T2");
        LaneQuoteDTO q1 = new LaneQuoteDTO(); q1.setLaneId(1L); q1.setQuote(5000);
        LaneQuoteDTO q2 = new LaneQuoteDTO(); q2.setLaneId(2L); q2.setQuote(7000);
        LaneQuoteDTO q3 = new LaneQuoteDTO(); q3.setLaneId(1L); q3.setQuote(6000);
        LaneQuoteDTO q4 = new LaneQuoteDTO(); q4.setLaneId(2L); q4.setQuote(6500);
        t1.setLaneQuotes(Arrays.asList(q1, q2));
        t2.setLaneQuotes(Arrays.asList(q3, q4));
        service.storeInput(Arrays.asList(lane1, lane2), Arrays.asList(t1, t2));
        AssignmentResponseDTO response = service.assignTransporters(2);
        assertEquals("success", response.getStatus());
        assertEquals(11500, response.getTotalCost());
        assertEquals(2, response.getAssignments().size());
        assertTrue(response.getSelectedTransporters().containsAll(Arrays.asList(1L, 2L)));
    }
} 