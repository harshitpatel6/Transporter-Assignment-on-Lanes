package com.example.transporteroptimizer.dto;

import java.util.List;

public class TransporterDTO {
    private Long id;
    private String name;
    private List<LaneQuoteDTO> laneQuotes;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<LaneQuoteDTO> getLaneQuotes() { return laneQuotes; }
    public void setLaneQuotes(List<LaneQuoteDTO> laneQuotes) { this.laneQuotes = laneQuotes; }
} 