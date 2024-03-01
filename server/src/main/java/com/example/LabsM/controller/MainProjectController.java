package com.example.LabsM.controller;

import com.example.LabsM.annotation.CustomExceptionHandler;
import com.example.LabsM.entity.Airport;
import com.example.LabsM.entity.Booking;
import com.example.LabsM.entity.Flight;
import com.example.LabsM.entity.User;
import com.example.LabsM.jpa.model.BookingModel;
import com.example.LabsM.jpa.model.UserModel;
import com.example.LabsM.service.DBService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/airport")
@CustomExceptionHandler
@CrossOrigin(origins = "http://localhost:3000")
public class MainProjectController {
    private DBService airportService;
    public MainProjectController(DBService airportService) {
        this.airportService = airportService;
    }
    @GetMapping("/addairline")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> addAirline(@RequestParam String name, @RequestParam String logo, @RequestParam String code) {
        List<Object> response = new ArrayList<>();
        Integer exAr = airportService.getAirlineId(name);
        if(exAr == -1) {
            airportService.saveOneAirline(name, code, logo);
            response.add(Boolean.FALSE);
        } else {
            response.add(Boolean.TRUE);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("deleteairline")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteAirline(@RequestParam String name) {
        List<Object> response = new ArrayList<>();
        Integer exAr = airportService.getAirlineId(name);
        if(exAr != -1) {
            airportService.deleteAirline(exAr);
            response.add(Boolean.FALSE);
        } else {
            response.add(Boolean.TRUE);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/home")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getHome() {
        List<Airport> list = airportService.getAllAirports();
        List<Airport> response = new ArrayList<>();
        response.add(list.get(0));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/airports")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getAirports() {
        List<Airport> response = airportService.getAllAirports();
        response.remove(0);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("deleteairport")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteAirport(@RequestParam String country) {
        List<Object> response = new ArrayList<>();
        Integer exAr = airportService.getAirportId(country);
        if(exAr != -1) {
            airportService.deleteAirport(exAr);
            response.add(Boolean.FALSE);
        } else {
            response.add(Boolean.TRUE);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("addairport")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> addAirport(@RequestParam String name, @RequestParam String country, @RequestParam Float coord1,
                           @RequestParam Float coord2) {
        List<Object> response = new ArrayList<>();
        Integer exAr = airportService.getAirportId(country);
        if(exAr == -1) {
            airportService.saveOneAirport(name, country, coord1, coord2);
            response.add(Boolean.FALSE);
        } else {
            response.add(Boolean.TRUE);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/bookingSearch")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> bookingSearch(@RequestParam String destination, @RequestParam Integer amount,
                                                @RequestParam String date) {
        List<Object> response = new ArrayList<>();
        Flight resp = airportService.getForBooking(destination, amount, date);
        response.add(resp);
        if(!resp.getDestination().getCountry().equals(destination)) {
            response.add(Boolean.FALSE);
        } else {
            response.add(Boolean.TRUE);
        };
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/bookings")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> bookings(@RequestParam String email) {
        List<Object> response = new ArrayList<>();
        List<BookingModel> nlist = airportService.getAllBookingsWithId();
        nlist.forEach(a -> {
            if(a.getUserid().equals(email)) {
                response.add(a);
                System.out.println(a.getFlightid());
                System.out.println(a.getUserid());
            }
        });
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/addbooking")
    @ResponseStatus(HttpStatus.OK)
    public void addBooking(@RequestParam String email, @RequestParam String airline,
                           @RequestParam Integer number, @RequestParam String date) {
        airportService.saveOneBooking(email, airline + number, date);
    }
    @GetMapping("/flights")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getFlights() {
        List<Flight> response = airportService.getAllFlights();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/addflight")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> addFlight(@RequestParam String destination, @RequestParam String airline,
                          @RequestParam String airplane, @RequestParam String days, @RequestParam String time,
                          @RequestParam Integer number) {
        List<Object> response = new ArrayList<>();
        Integer err = 0;
        Integer exDe = airportService.getAirportId(destination);
        Integer exAl = airportService.getAirlineId(airline);
        if(exDe == -1) {
            err = 1;
            response.add(Boolean.FALSE);
        } else {
            response.add(Boolean.TRUE);
        };
        if(exAl == -1) {
            err = 1;
            response.add(Boolean.FALSE);
        } else {
            response.add(Boolean.TRUE);
        };
        Integer k = 0;
        if((k = airportService.getFlightByNumber(airline, number)) != -1) {
            err = 1;
            response.add(Boolean.FALSE);
        } else {
            response.add(Boolean.TRUE);
        };
        if(err == 0) {
            airportService.saveOneFlight(destination, airline, airplane, days, time, number);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("deleteflight")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteFlight(@RequestParam String airline, @RequestParam Integer number) {
        List<Object> response = new ArrayList<>();
        Integer k = 0;
        if((k = airportService.getFlightByNumber(airline, number)) != -1) {
            response.add(Boolean.FALSE);
            airportService.deleteFlight(k);
        } else {
            response.add(Boolean.TRUE);
        };
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public void getUsers() {

    }
    @GetMapping("/adduser")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> addUser(@RequestParam String email, @RequestParam String password, @RequestParam Integer login) {
        Integer userStatus = airportService.getUserId(email);
        List<Object> resNew = new ArrayList<>();
        resNew.add(email);
        if(userStatus < 0) {
            if(login == 0) {
                airportService.saveOneUser(email, password);
                Integer userid = airportService.getUserId(email);
                Boolean isAdmin = airportService.getUserWithId(userid).getIsadmin();
                String newPassword = airportService.getUserWithId(userid).getPassword();
                resNew.add(userid);
                resNew.add(newPassword);
                resNew.add(isAdmin);
            } else {
                Integer userid = -1;
                Boolean isAdmin = Boolean.FALSE;
                String newPassword = "";
                resNew.add(userid);
                resNew.add(newPassword);
                resNew.add(isAdmin);
            }
            resNew.add(Boolean.TRUE);
            return new ResponseEntity<>(resNew, HttpStatus.CREATED);
        }
        List<Object> resOld = new ArrayList<>();
        Boolean isOldAdmin = airportService.getUserWithId(userStatus).getIsadmin();
        String oldPassword = airportService.getUserWithId(userStatus).getPassword();
        resOld.add(email);
        resOld.add(userStatus);
        resOld.add(oldPassword);
        resOld.add(isOldAdmin);
        resOld.add(Boolean.FALSE);
        return new ResponseEntity<>(resOld, HttpStatus.OK);
    }


}
