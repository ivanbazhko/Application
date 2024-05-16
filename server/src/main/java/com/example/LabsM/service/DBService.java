package com.example.LabsM.service;

import com.example.LabsM.entity.*;
import com.example.LabsM.jpa.*;
import com.example.LabsM.jpa.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class DBService {
    private AirlineJpaRepository airlineRepository;
    private AirportJpaRepository airportRepository;
    private BookingJpaRepository bookingRepository;
    private FlightJpaRepository flightRepository;
    private UserJpaRepository userRepository;
    private PictureJpaRepository pictureRepository;
    public DBService(AirlineJpaRepository airlineRepository,
                     AirportJpaRepository airportRepository,
                     BookingJpaRepository bookingRepository,
                     FlightJpaRepository flightRepository,
                     UserJpaRepository userRepository,
                     PictureJpaRepository pictureRepository) {
        this.airlineRepository = airlineRepository;
        this.airportRepository = airportRepository;
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
    }
    ////////////////////////////////////////////////////////////////////////////

    public void savePicture(byte[] image) {
        PictureModel model = new PictureModel(image);
        pictureRepository.save(model);
    }

    public List<byte[]> getAllImages() {
        List<byte[]> result = new ArrayList<>();
        pictureRepository.findAll().forEach(a -> {
            result.add(a.getImage());
        });
        return result;
    }

    ////////////////////////////////////////////////////////////////////////////
    public void saveOneAirline(String name, String code, String logo) {
        AirlineModel model = new AirlineModel(name, code, logo);
        airlineRepository.save(model);
    }
    public List<AirlineModel> getAllAirlinesWithId() {
        List<AirlineModel> result = new ArrayList<>();
        airlineRepository.findAll().forEach(a -> {
            result.add(a);
        });
        return result;
    }
    public AirlineModel getAirlineWithId(Integer id) {
        AirlineModel result = airlineRepository.findById(id).get();
        return result;
    }
    public List<Airline> getAllAirlines() {
        List<Airline> result = new ArrayList<>();
        airlineRepository.findAll().forEach(a -> {
            result.add(new Airline(a.getName(), a.getCode(), a.getLogo()));
        });
        return result;
    }
    public void deleteAirline(Integer airlineId) {
        airlineRepository.deleteById(airlineId);
    }
    public Integer getAirlineId(String name) {
        List<AirlineModel> allAirlines = getAllAirlinesWithId();
        Integer result = -1;
        for(int i = 0; i < allAirlines.size(); i++) {
            if(allAirlines.get(i).getName().equals(name)) {
                result = allAirlines.get(i).getId();
            }
        };
        return result;
    }
    ////////////////////////////////////////////////////////////////////////////
    public void saveOneAirport(String name, String country, Float coord1, Float coord2) {
        AirportModel model = new AirportModel(name, country, coord1, coord2);
        airportRepository.save(model);
    }
    public List<AirportModel> getAllAirportsWithId() {
        List<AirportModel> result = new ArrayList<>();
        airportRepository.findAll().forEach(a -> {
            result.add(a);
        });
        return result;
    }
    public AirportModel getAirportWithId(Integer id) {
        AirportModel result = airportRepository.findById(id).get();
        return result;
    }
    public List<Airport> getAllAirports() {
        List<Airport> result = new ArrayList<>();
        airportRepository.findAll().forEach(a -> {
            result.add(new Airport(a.getName(), a.getCountry(), a.getCoord1(), a.getCoord2()));
        });
        return result;
    }
//    public List<AirportModel> getAllAirportsWithId() {
//        List<AirportModel> result = new ArrayList<>();
//        airportRepository.findAll().forEach(a -> {
//            result.add(new AirportModel(a.getName(), a.getCountry(), a.getCoord1(), a.getCoord2()));
//        });
//        return result;
//    }
    public void deleteAirport(Integer airportId) {
        airportRepository.deleteById(airportId);
    }
    public Integer getAirportId(String country) {
        List<AirportModel> allAirports = getAllAirportsWithId();
        Integer result = -1;
        for(int i = 0; i < allAirports.size(); i++) {
            if(allAirports.get(i).getCountry().equals(country)) {
                result = allAirports.get(i).getId();
            }
        };
        return result;
    }
    ////////////////////////////////////////////////////////////////////////////
    public void saveOneUser(String name, String password) {
        UserModel model = new UserModel(name, password);
        userRepository.save(model);
    }
    public List<UserModel> getAllUsersWithId() {
        List<UserModel> result = new ArrayList<>();
        userRepository.findAll().forEach(a -> {
            result.add(a);
        });
        return result;
    }
    public UserModel getUserWithId(Integer id) {
        UserModel result = userRepository.findById(id).get();
        return result;
    }
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        userRepository.findAll().forEach(a -> {
            result.add(new User(a.getName(), a.getPassword()));
        });
        return result;
    }
    public Integer getUserId(String name) {
        List<UserModel> allUsers = getAllUsersWithId();
        Integer result = -1;
        for(int i = 0; i < allUsers.size(); i++) {
            if(allUsers.get(i).getName().equals(name)) {
                result = allUsers.get(i).getId();
            }
        };
        return result;
    }
    ////////////////////////////////////////////////////////////////////////////
    public void saveOneBooking(String userid, String flightid, String date, String time, Float price, String route) {
        List<Flight> flights = getAllFlights();
        Float priceone = (float)1;
        Integer realnum = Integer.parseInt(flightid.substring(2));
        if(realnum % 2 == 0) realnum -= 1;
        String nn = flightid.substring(0, 2) + realnum.toString();
        for(int i = 0; i < flights.size(); i++) {
            String num = flights.get(i).getAirline().getCode() + flights.get(i).getNumber().toString();
            if(num.equals(nn)) {
                priceone = flights.get(i).getPrice();
            }
        }
        System.out.println(priceone);
        Integer amount = Math.round(price / priceone);
        BookingModel model = new BookingModel(userid, flightid, date, time, price, amount, route);
        bookingRepository.save(model);
    }
    public List<BookingModel> getAllBookingsWithId() {
        List<BookingModel> result = new ArrayList<>();
        bookingRepository.findAll().forEach(a -> {
            result.add(a);
        });
        return result;
    }
    public BookingModel getBookingWithId(Integer id) {
        BookingModel result = bookingRepository.findById(id).get();
        return result;
    }
//    public List<Booking> getAllBooking() {
//        List<Booking> result = new ArrayList<>();
//        bookingRepository.findAll().forEach(a -> {
//            result.add(new Booking(getAllUsers().get(a.getUserid()), getAllFlights().get(a.getFlightid()), a.getDate()));
//        });
//        return result;
//    }

    ////////////////////////////////////////////////////////////////////////////
    public void saveOneFlight(String destination, String airline,
                              String airplane, String days, String time, Integer number, Integer origin, Float price) {
        FlightModel model = new FlightModel(getAirportId(destination),
                getAirlineId(airline), airplane, days, time, number, origin, price);
        flightRepository.save(model);
    }
    public List<FlightModel> getAllFlightsWithId() {
        List<FlightModel> result = new ArrayList<>();
        flightRepository.findAll().forEach(a -> {
            result.add(a);
        });
        return result;
    }
    public FlightModel getFlightWithId(Integer id) {
        FlightModel result = flightRepository.findById(id).get();
        return result;
    }
    public List<Object> getAllFlightsForDisplay() {
        List<Object> result = new ArrayList<>();
        return result;
    }
    public Integer getFlightByNumber(String airline, Integer number) {
        List<FlightModel> allFlights = flightRepository.findAll();
        Integer result = -1;
        for(int i = 0; i < allFlights.size(); i++) {
            if(getAirlineWithId(allFlights.get(i).getAirlineid()).getName().equals(airline) &&
            allFlights.get(i).getNumber().equals(number)) {
                result = allFlights.get(i).getId();
                break;
            }
        }
        return result;
    }
    public List<Flight> getAllFlights() {
        List<Flight> result = new ArrayList<>();
        flightRepository.findAll().forEach(a -> {
            List<Airport> airports = getAllAirports();
            List<Airline> airlines = getAllAirlines();
            Integer airportId = -1;
            Integer airlineId = -1;
            for(int i = 0; i < airports.size(); i++) {
                if(getAirportId(airports.get(i).getCountry()).equals(a.getDestinationid())) {
                    airportId = i;
                    break;
                }
            }
            for(int i = 0; i < airlines.size(); i++) {
                if(getAirlineId(airlines.get(i).getName()).equals(a.getAirlineid())) {
                    airlineId = i;
                    break;
                }
            }
            result.add(new Flight(getAllAirports().get(airportId),
                    getAllAirlines().get(airlineId), a.getAirplaneid(), a.getDays(),
                    a.getTime(), a.getNumber(), a.getOrigin(), a.getPrice()));
        });
        return result;
    }
    public void deleteFlight(Integer id) {
        flightRepository.deleteById(id);
    }
}
