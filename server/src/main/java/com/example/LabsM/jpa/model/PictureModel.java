package com.example.LabsM.jpa.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pictures")
public class PictureModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "image")
    private byte[] image;

    public PictureModel(Integer id, byte[] image) {
        this.id = id;
        this.image = image;
    }

    public PictureModel() {
        this.id = 0;
        this.image = null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public PictureModel(byte[] image) {
        this.image = image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
