package com.example.transporteroptimizer.controller;

import com.example.transporteroptimizer.service.TransporterAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.transporteroptimizer.dto.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transporters")
public class TransporterAssignmentController {
    private final TransporterAssignmentService assignmentService;

    @Autowired
    public TransporterAssignmentController(TransporterAssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping("/input")
    public ResponseEntity<?> inputData(@RequestBody InputRequestDTO input) {
        assignmentService.storeInput(input.getLanes(), input.getTransporters());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/assignment")
    public ResponseEntity<AssignmentResponseDTO> assignTransporters(@RequestBody AssignmentRequestDTO request) {
        AssignmentResponseDTO response = assignmentService.assignTransporters(request.getMaxTransporters());
        return ResponseEntity.ok(response);
    }
} 