package com.example.LabsM.jpa.model;

import jakarta.persistence.*;

@Entity
@Table(name = "airlines")
public class AirlineModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
    @Column(name = "logo")
    private String logo;

    public AirlineModel(Integer id, String name, String code, String logo) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.logo = logo;
    }

    public AirlineModel() {
        this.id = 0;
        this.name = "";
        this.code = "";
        this.logo = "";
    }

    public AirlineModel(String name, String code, String logo) {
        this.name = name;
        this.code = code;
        this.logo = logo;
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
}
