package com.example.LabsM.jpa.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "isadmin")
    private Boolean isadmin;

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

    public Boolean getIsadmin() {
        return isadmin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserModel() {
        this.id = 0;
        this.name = "";
        this.password = "";
        this.isadmin = Boolean.FALSE;
    }
    public UserModel(Integer id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.isadmin = Boolean.FALSE;
    }

    public UserModel(String name, String password) {
        this.name = name;
        this.password = password;
        this.isadmin = Boolean.FALSE;
    }
}
