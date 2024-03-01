package com.example.LabsM.jpa;

import com.example.LabsM.jpa.model.AirportModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportJpaRepository extends JpaRepository<AirportModel, Integer> {
}
