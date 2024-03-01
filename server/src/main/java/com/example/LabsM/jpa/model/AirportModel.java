package com.example.LabsM.jpa.model;

import jakarta.persistence.*;

@Entity
@Table(name = "airports")
public class AirportModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "country")
    private String country;
    @Column(name = "coord1")
    private Float coord1;
    private Float coord2;

    public AirportModel() {
        this.id = 0;
        this.name = "";
        this.country = "";
        this.coord1 = (float)0;
        this.coord2 = (float)0;
    }

    public AirportModel(Integer id, String name, String country, Float coord1, Float coord2) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.coord1 = coord1;
        this.coord2 = coord2;
    }

    public AirportModel(String name, String country, Float coord1, Float coord2) {
        this.name = name;
        this.country = country;
        this.coord1 = coord1;
        this.coord2 = coord2;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Float getCoord1() {
        return coord1;
    }

    public void setCoord1(Float coord1) {
        this.coord1 = coord1;
    }

    public Float getCoord2() {
        return coord2;
    }

    public void setCoord2(Float coord2) {
        this.coord2 = coord2;
    }
}
