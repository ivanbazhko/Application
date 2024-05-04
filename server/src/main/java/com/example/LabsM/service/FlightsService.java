package com.example.LabsM.service;

import com.example.LabsM.entity.Airline;
import com.example.LabsM.entity.AirplaneOnMap;
import com.example.LabsM.entity.Airport;
import com.example.LabsM.entity.Flight;
import com.example.LabsM.jpa.*;
import com.example.LabsM.jpa.model.AirportModel;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlightsService {
    private AirlineJpaRepository airlineRepository;
    private AirportJpaRepository airportRepository;
    private BookingJpaRepository bookingRepository;
    private FlightJpaRepository flightRepository;
    private UserJpaRepository userRepository;
    private DBService dbService;
    public FlightsService(AirlineJpaRepository airlineRepository,
                        AirportJpaRepository airportRepository,
                        BookingJpaRepository bookingRepository,
                        FlightJpaRepository flightRepository,
                        UserJpaRepository userRepository,
                        DBService dbService) {
        this.airlineRepository = airlineRepository;
        this.airportRepository = airportRepository;
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
        this.dbService = dbService;
    }
    ////////////////////////////////////////////////////////////////////////////
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double lat = Math.toRadians(lat2 - lat1);
        double lon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(lat / 2) * Math.sin(lat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(lon / 2) * Math.sin(lon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 6371 * c;
    }
    public Float calculatetimediff(Integer h1, Integer m1, Integer h2, Integer m2, Integer print) {
        Integer totalMinutesElapsed;
        Integer minutesElapsed;
        Double totalHoursElapsed;
        Integer hoursElapsed;
        Integer hourJump = 0;
        if (h1 <= h2) {
            hoursElapsed = h2 - h1;
        } else {
            hoursElapsed = 24 - h1 + h2;
        };
        if (m1 <= m2) {
            minutesElapsed = m2 - m1;
        } else {
            minutesElapsed = 60 - m1 + m2;
            if(h1 == h2) hourJump = -23;
            else hourJump = 1;
        };
        totalMinutesElapsed = (hoursElapsed - hourJump) * 60 + minutesElapsed;
        totalHoursElapsed = totalMinutesElapsed / 60.0;
        if(print == 1) {
            System.out.println("Time elapsed: " + totalHoursElapsed);
            System.out.println("Time elapsed in hours: " + (hoursElapsed - hourJump));
            System.out.println("Time elapsed in minutes: " + minutesElapsed);
        }
        return totalHoursElapsed.floatValue();
    }
    public List<AirplaneOnMap> getActiveFlights() {
        List<AirplaneOnMap> result = new ArrayList<>();
        Integer speed = 900;
        Integer timeout = 120;
        List<Flight> allflights = dbService.getAllFlights();
        List<Flight> allactiveflights;
        List<AirportModel> allairports = dbService.getAllAirportsWithId();
        Float homecoord1 = allairports.get(0).getCoord1();
        Float homecoord2 = allairports.get(0).getCoord2();
        String homename = allairports.get(0).getCountry();
        LocalTime currentTime = LocalTime.now();
        int currentHour = currentTime.getHour();
        int currentMinute = currentTime.getMinute();
//        System.out.println("Current time: " + currentHour + ":" + currentMinute);
        LocalDate currentDate = LocalDate.now();
        Integer currentDay = currentDate.getDayOfWeek().getValue();
//        System.out.println("Current day of the week: " + currentDay);
        allflights.forEach(a -> {
//            System.out.println("Days: " + a.getDays());
            Integer activity = 0;
            String airport = a.getDestination().getCountry();
            Integer airportId = dbService.getAirportId(airport);
            Float destcoord1 = dbService.getAirportWithId(airportId).getCoord1();
            Float destcoord2 = dbService.getAirportWithId(airportId).getCoord2();
            Double totaldistance0 = calculateDistance(homecoord1.doubleValue(),
                    homecoord2.doubleValue(), destcoord1.doubleValue(), destcoord2.doubleValue());
            Float totaldistance = totaldistance0.floatValue();
            Float flightTimeHours = totaldistance / speed;
            String[] timeParts = a.getTime().split(":");
            int dephour = Integer.parseInt(timeParts[0]);
            int depminute = Integer.parseInt(timeParts[1]);
//            System.out.println("---------------------------------------");
            Float elapsedTime = calculatetimediff(dephour, depminute, currentHour, currentMinute, 0);
//            System.out.println("-----");
            Float elapsedTimeToday = calculatetimediff(0, 0, currentHour, currentMinute, 0);
//            System.out.println("-----");
            Float revolveTime = 2 * flightTimeHours + (timeout / 60);
            Integer maxActive = (int)(Math.ceil(revolveTime / 24));

            for(int i = 0; i < maxActive; i++) {
                Integer dayLeft = currentDay;
                if((elapsedTime + 24 * i) < revolveTime) {
                    if((elapsedTime + 24 * i) > elapsedTimeToday) {
                        Integer daysCount = (int)(Math.ceil((elapsedTime - elapsedTimeToday + 24 * i) / 24));
                        dayLeft -= daysCount;
                        if(dayLeft < 1) dayLeft += 7;
                    };
//                    System.out.println("Max Active: " + maxActive);
//                    System.out.println("Day Left: " + dayLeft);
                    if(a.getDays().contains(dayLeft.toString())) {
                        activity += 1;
                        Integer direction;
                        String fnstring;
                        String dirstring = "";
                        Integer progress;
                        Float travelled;
                        String departure;
                        String arrival;
                        Integer flighthours = (int)Math.floor(flightTimeHours);
                        Integer flightminutes = Math.round((flightTimeHours - flighthours) * 60);
                        if(elapsedTime >= flightTimeHours && elapsedTime <= flightTimeHours + (timeout / 60)) continue;
                        if((elapsedTime + 24 * i) < (revolveTime / 2)) {
                            direction = 1;
                            fnstring = a.getAirline().getCode() + ' ' + a.getNumber().toString();
                            dirstring = homename + " -> " + airport;
                            travelled = elapsedTime * speed;
                            departure = a.getTime();
                            Integer arrhours = dephour + flighthours;
                            Integer arrminutes = depminute + flightminutes;
                            if(arrminutes > 59) {
                                arrminutes -= 60;
                                arrhours += 1;
                            };
                            while(arrhours > 23) {
                                arrhours -= 24;
                            };
                            String finarrmin = arrminutes.toString();
                            if (finarrmin.length() == 1) finarrmin = '0' + finarrmin;
                            arrival = arrhours.toString() + ':' + finarrmin;
                        } else {
                            direction = -1;
                            Integer newnum = a.getNumber() + 1;
                            fnstring = a.getAirline().getCode() + ' ' + newnum.toString();
                            dirstring = airport + " -> " + homename;
                            travelled = ((elapsedTime + 24 * i) - flightTimeHours - (timeout / 60)) * speed;
                            Integer dephours = dephour + flighthours + (int)Math.floor(timeout / 60);
                            Integer depminutes = depminute + flightminutes + (timeout - ((int)Math.floor(timeout / 60) * 60));
                            while(depminutes > 59) {
                                depminutes -= 60;
                                dephours += 1;
                            };
                            if(dephours > 23) {
                                dephours -= 24;
                            };
                            String findepmin = depminutes.toString();
                            if (findepmin.length() == 1) findepmin = '0' + findepmin;
                            departure = dephours.toString() + ':' + findepmin;
                            Integer arrhours = dephours + flighthours;
                            Integer arrminutes = depminutes + flightminutes;
                            if(arrminutes > 59) {
                                arrminutes -= 60;
                                arrhours += 1;
                            };
                            while(arrhours > 23) {
                                arrhours -= 24;
                            };
                            String finarrmin = arrminutes.toString();
                            if (finarrmin.length() == 1) finarrmin = '0' + finarrmin;
                            arrival = arrhours.toString() + ':' + finarrmin;
                        };
//                        System.out.println("Flight time: " + flighthours + ':' + flightminutes);
//                        System.out.println("Travelled: " + travelled);
//                        System.out.println("Total distance: " + totaldistance);
                        progress = Math.round(travelled / totaldistance * 1000);
                        if(direction == -1) progress = 1000 - progress;
                        String image = a.getAirline().getLogo();
                        AirplaneOnMap figure = new AirplaneOnMap(a.getDestination().getCoord1(), a.getDestination().getCoord2(),
                                    airport, direction, progress, dirstring, fnstring, image, departure, arrival, a.getAirplane());
                        result.add(figure);
                    }
                }
            }

//            System.out.println(homename + " " + airport + " " + totaldistance.toString() + " " + flightTimeHours.toString());
//            System.out.println("Departure time: " + a.getTime());
//            System.out.println("Activity: " + activity);

        });
        return result;
    }
}
