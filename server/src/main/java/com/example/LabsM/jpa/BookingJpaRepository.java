package com.example.LabsM.jpa;

import com.example.LabsM.jpa.model.BookingModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingJpaRepository extends JpaRepository<BookingModel, Integer> {
}
