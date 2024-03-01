package com.example.LabsM.jpa;

import com.example.LabsM.jpa.model.AirlineModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirlineJpaRepository extends JpaRepository<AirlineModel, Integer> {
}