package com.example.LabsM.jpa.model;

import jakarta.persistence.*;

@Entity
@Table(name = "flights")
public class FlightModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "destinationid")
    private Integer destinationid;
    @Column(name = "airlineid")
    private Integer airlineid;
    @Column(name = "airplaneid")
    private String airplaneid;
    @Column(name = "days")
    private String days;
    @Column(name = "time")
    private String time;
    @Column(name = "number")
    private Integer number;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDestinationid() {
        return destinationid;
    }

    public void setDestinationid(Integer destinationid) {
        this.destinationid = destinationid;
    }

    public Integer getAirlineid() {
        return airlineid;
    }

    public void setAirlineid(Integer airlineid) {
        this.airlineid = airlineid;
    }

    public String getAirplaneid() {
        return airplaneid;
    }

    public void setAirplaneid(String airplaneid) {
        this.airplaneid = airplaneid;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public FlightModel() {
        this.id = -1;
        this.destinationid = -1;
        this.airlineid = -1;
        this.airplaneid = "";
        this.days = "";
        this.time = "";
        this.number = -1;
    }

    public FlightModel(Integer id, Integer destinationid, Integer airlineid,
                       String airplaneid, String days, String time, Integer number) {
        this.id = id;
        this.destinationid = destinationid;
        this.airlineid = airlineid;
        this.airplaneid = airplaneid;
        this.days = days;
        this.time = time;
        this.number = number;
    }

    public FlightModel(Integer destinationid, Integer airlineid,
                       String airplaneid, String days, String time, Integer number) {
        this.destinationid = destinationid;
        this.airlineid = airlineid;
        this.airplaneid = airplaneid;
        this.days = days;
        this.time = time;
        this.number = number;
    }
}
