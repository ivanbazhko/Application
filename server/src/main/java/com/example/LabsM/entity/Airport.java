package com.example.LabsM.entity;

public class Airport {
    private String name;
    private String country;
    private Float coord1;
    private Float coord2;

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

    public Airport(String name, String country, Float coord1, Float coord2) {
        this.name = name;
        this.country = country;
        this.coord1 = coord1;
        this.coord2 = coord2;
    }

}
