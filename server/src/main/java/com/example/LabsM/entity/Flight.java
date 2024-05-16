package com.example.LabsM.entity;

public class Flight {
    private Airport destination;
    private Airline airline;
    private String airplane;
    private String days;
    private String time;
    private Integer number;
    private Integer origin;
    private Float price;

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public Flight(Airport destination, Airline airline, String airplane, String days,
                  String time, Integer number, Integer origin, Float price) {
        this.destination = destination;
        this.airline = airline;
        this.airplane = airplane;
        this.days = days;
        this.time = time;
        this.number = number;
        this.origin = origin;
        this.price = price;
    }

    public Flight() {
        this.destination = null;
        this.airline = null;
        this.airplane = "";
        this.days = "";
        this.time = "";
        this.number = -1;
        this.origin = 1;
        this.price = (float)0;
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public String getAirplane() {
        return airplane;
    }

    public void setAirplane(String airplane) {
        this.airplane = airplane;
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

}
