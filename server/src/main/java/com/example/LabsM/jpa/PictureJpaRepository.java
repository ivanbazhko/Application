package com.example.LabsM.jpa;

import com.example.LabsM.jpa.model.PictureModel;
import com.example.LabsM.jpa.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureJpaRepository  extends JpaRepository<PictureModel, Integer> {
}
