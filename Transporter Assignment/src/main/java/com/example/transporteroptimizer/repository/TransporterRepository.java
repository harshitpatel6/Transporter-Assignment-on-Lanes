package com.example.transporteroptimizer.repository;

import com.example.transporteroptimizer.model.Transporter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransporterRepository extends JpaRepository<Transporter, Long> {
} 