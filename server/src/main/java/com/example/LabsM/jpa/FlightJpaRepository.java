package com.example.LabsM.jpa;

import com.example.LabsM.jpa.model.FlightModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightJpaRepository extends JpaRepository<FlightModel, Integer> {
}
