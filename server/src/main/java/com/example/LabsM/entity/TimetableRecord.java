package com.example.LabsM.entity;

public class TimetableRecord {
    private String logo;
    private String direction;
    private String number;
    private String time;
    private String airplane;
    private String days;

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public TimetableRecord(String logo, String direction, String number, String time, String airplane, String days) {
        this.logo = logo;
        this.direction = direction;
        this.number = number;
        this.time = time;
        this.airplane = airplane;
        this.days = days;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
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

    public String getAirplane() {
        return airplane;
    }

    public TimetableRecord() {
        this.logo = "";
        this.direction = "";
        this.number = "";
        this.time = "";
        this.airplane = "";
        this.days = "";
    }

    public void setAirplane(String airplane) {
        this.airplane = airplane;
    }
}
