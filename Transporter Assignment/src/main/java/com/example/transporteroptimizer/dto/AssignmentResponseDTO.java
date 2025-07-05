package com.example.transporteroptimizer.dto;

import java.util.List;

public class AssignmentResponseDTO {
    private String status;
    private int totalCost;
    private List<AssignmentDTO> assignments;
    private List<Long> selectedTransporters;

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getTotalCost() { return totalCost; }
    public void setTotalCost(int totalCost) { this.totalCost = totalCost; }
    public List<AssignmentDTO> getAssignments() { return assignments; }
    public void setAssignments(List<AssignmentDTO> assignments) { this.assignments = assignments; }
    public List<Long> getSelectedTransporters() { return selectedTransporters; }
    public void setSelectedTransporters(List<Long> selectedTransporters) { this.selectedTransporters = selectedTransporters; }
} 