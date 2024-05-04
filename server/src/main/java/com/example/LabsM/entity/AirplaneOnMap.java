package com.example.LabsM.entity;

public class AirplaneOnMap {
    private Float coord1;
    private Float coord2;
    private String airport;
    private Integer direction;
    private Integer progress;
    private String route;
    private String number;
    private String logo;
    private String departure;
    private String arrival;
    private String airplane;

    public AirplaneOnMap(Float coord1, Float coord2, String airport, Integer direction, Integer progress,
                         String route, String number, String logo, String departure, String arrival, String airplane) {
        this.coord1 = coord1;
        this.coord2 = coord2;
        this.airport = airport;
        this.direction = direction;
        this.progress = progress;
        this.route = route;
        this.number = number;
        this.logo = logo;
        this.departure = departure;
        this.arrival = arrival;
        this.airplane = airplane;
    }

    public String getAirplane() {
        return airplane;
    }

    public void setAirplane(String airplane) {
        this.airplane = airplane;
    }

    public Float getCoord1() {
        return coord1;
    }

    public void setCoord1(Float coord1) {
        this.coord1 = coord1;
    }

    public Float getCoord2() {
        return coord2;
    }

    public void setCoord2(Float coord2) {
        this.coord2 = coord2;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }
}
