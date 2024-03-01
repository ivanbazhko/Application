package com.example.LabsM.entity;

public class Booking {
    private User user;
    private Flight flight;
    private String date;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Booking(User user, Flight flight, String date) {
        this.user = user;
        this.flight = flight;
        this.date = date;
    }
}
