package com.example.LabsM.entity;

public class Search {
    private String destination;
    private String code;
    private String airline;
    private String number;
    private String time;
    private String logo;
    private Float price;
    private String flighttime;
    private String airplane;
    public String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAirplane() {
        return airplane;
    }

    public void setAirplane(String airplane) {
        this.airplane = airplane;
    }

    public Search(String destination, String code, String airline, String number, String time, String logo, Float price, String flighttime,
                  String airplane, String date) {
        this.destination = destination;
        this.code = code;
        this.airline = airline;
        this.number = number;
        this.time = time;
        this.logo = logo;
        this.price = price;
        this.flighttime = flighttime;
        this.airplane = airplane;
        this.date = date;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getFlighttime() {
        return flighttime;
    }

    public void setFlighttime(String flighttime) {
        this.flighttime = flighttime;
    }
}
