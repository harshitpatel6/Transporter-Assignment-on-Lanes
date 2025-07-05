package com.example.transporteroptimizer.dto;

import java.util.List;

public class InputRequestDTO {
    private List<LaneDTO> lanes;
    private List<TransporterDTO> transporters;

    public List<LaneDTO> getLanes() { return lanes; }
    public void setLanes(List<LaneDTO> lanes) { this.lanes = lanes; }
    public List<TransporterDTO> getTransporters() { return transporters; }
    public void setTransporters(List<TransporterDTO> transporters) { this.transporters = transporters; }
} 