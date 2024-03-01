package com.example.LabsM.entity;

public class Airline {
    private String name;
    private String code;
    private String logo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Airline(String name, String code,String logo) {
        this.name = name;
        this.code = code;
        this.logo = logo;
    }
}
