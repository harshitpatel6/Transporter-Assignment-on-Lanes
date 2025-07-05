package com.example.transporteroptimizer.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Transporter {
    @Id
    private Long id;
    private String name;

    @OneToMany(mappedBy = "transporter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LaneQuote> laneQuotes;

    public Transporter() {}

    public Transporter(Long id, String name, List<LaneQuote> laneQuotes) {
        this.id = id;
        this.name = name;
        this.laneQuotes = laneQuotes;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<LaneQuote> getLaneQuotes() { return laneQuotes; }
    public void setLaneQuotes(List<LaneQuote> laneQuotes) { this.laneQuotes = laneQuotes; }
} 