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
    @Column(name = "time")
    private String time;
    @Column(name = "price")
    private Float price;
    @Column(name = "amount")
    private Integer amount;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    @Column(name = "route")
    private String route;

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

    public BookingModel(Integer id, String userid, String flightid, String date, String time, Float price,
                        Integer amount, String route) {
        this.id = id;
        this.userid = userid;
        this.flightid = flightid;
        this.date = date;
        this.time = time;
        this.price = price;
        this.amount = amount;
        this.route = route;
    }

    public BookingModel(String userid, String flightid, String date, String time, Float price,
                        Integer amount, String route) {
        this.userid = userid;
        this.flightid = flightid;
        this.date = date;
        this.time = time;
        this.price = price;
        this.amount = amount;
        this.route = route;
    }

    public BookingModel() {
        this.userid = "";
        this.flightid = "";
        this.date = "";
        this.time = "";
        this.price = (float)0;
        this.amount = 0;
        this.route = "";
    }
}
