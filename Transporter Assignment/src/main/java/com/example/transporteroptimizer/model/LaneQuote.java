package com.example.transporteroptimizer.model;

import jakarta.persistence.*;

@Entity
public class LaneQuote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lane_id")
    private Lane lane;

    @ManyToOne
    @JoinColumn(name = "transporter_id")
    private Transporter transporter;

    private int quote;

    public LaneQuote() {}

    public LaneQuote(Lane lane, Transporter transporter, int quote) {
        this.lane = lane;
        this.transporter = transporter;
        this.quote = quote;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Lane getLane() { return lane; }
    public void setLane(Lane lane) { this.lane = lane; }
    public Transporter getTransporter() { return transporter; }
    public void setTransporter(Transporter transporter) { this.transporter = transporter; }
    public int getQuote() { return quote; }
    public void setQuote(int quote) { this.quote = quote; }
} 