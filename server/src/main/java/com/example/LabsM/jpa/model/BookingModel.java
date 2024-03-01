package com.example.LabsM.jpa.model;

import com.example.LabsM.entity.Flight;
import com.example.LabsM.entity.User;
import jakarta.persistence.*;

@Entity
@Table(name = "bookings")
public class BookingModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "userid")
    private String userid;
    @Column(name = "flightid")
    private String flightid;
    @Column(name = "date")
    private String date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFlightid() {
        return flightid;
    }

    public void setFlightid(String flightid) {
        this.flightid = flightid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BookingModel(Integer id, String userid, String flightid, String date) {
        this.id = id;
        this.userid = userid;
        this.flightid = flightid;
        this.date = date;
    }

    public BookingModel(String userid, String flightid, String date) {
        this.userid = userid;
        this.flightid = flightid;
        this.date = date;
    }

    public BookingModel() {
        this.userid = "";
        this.flightid = "";
        this.date = "";
    }
}
