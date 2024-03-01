package com.example.LabsM.jpa;

import com.example.LabsM.jpa.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserModel, Integer> {
}
