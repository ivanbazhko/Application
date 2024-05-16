package com.example.LabsM.controller;

import com.example.LabsM.annotation.CustomExceptionHandler;
import com.example.LabsM.entity.*;
import com.example.LabsM.jpa.model.BookingModel;
import com.example.LabsM.jpa.model.UserModel;
import com.example.LabsM.service.DBService;
import com.example.LabsM.service.FlightsService;
import com.example.LabsM.service.MailService;
import com.example.LabsM.service.SockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/airport")
@CustomExceptionHandler
@CrossOrigin(origins = "http://localhost:3000")
public class MainProjectController {
    private DBService airportService;
    private MailService mailService;
    private FlightsService flightsService;
    private SockService sockService;
    public MainProjectController(DBService airportService, MailService mailService, FlightsService flightsService, SockService sockService) {
        this.airportService = airportService;
        this.mailService = mailService;
        this.flightsService = flightsService;
        this.sockService = sockService;
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
                                                @RequestParam String date, @RequestParam Integer origin) {
        List<Object> response = new ArrayList<>();
        List<Search> resp = flightsService.getForBooking(destination, amount, date, origin);
        response.add(resp);
        return new ResponseEntity<>(resp, HttpStatus.OK);
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
    public void addBooking(@RequestParam String email, @RequestParam String number,
                           @RequestParam String date, @RequestParam String time, @RequestParam Float price, @RequestParam String route) {
        airportService.saveOneBooking(email, number, date, time, price, route);
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
                          @RequestParam Integer number, @RequestParam Integer origin, @RequestParam Float price) {
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
            airportService.saveOneFlight(destination, airline, airplane, days, time, number, origin, price);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/deleteflight")
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

    @GetMapping("/sendconfirmationmail")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> sendConfirm(@RequestParam String email) {
        return new ResponseEntity<>(mailService.sendConfirmationCode(email), HttpStatus.OK);
    };

    @GetMapping("/getactiveflights")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getFlyingPlanes() {
        System.out.println("Requested active flights");
        return new ResponseEntity<>(flightsService.getActiveFlights(), HttpStatus.OK);
    };

    @GetMapping("/getdomesticarrivals")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getDomesticArrivals() {
        System.out.println("Requested domestic arrivals");
        return null;
    };

    @GetMapping("/getallarrivals")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getAllArrivals() {
        System.out.println("Requested all arrivals");
        return null;
    };

    @GetMapping("/getdomesticdepartures")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getDomesticDepartures() {
        System.out.println("Requested domestic departures");
        return null;
    };

    @GetMapping("/getalldepartures")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getAllDepartures() {
        System.out.println("Requested all departures");
        return new ResponseEntity<>(flightsService.getAllDepartures(), HttpStatus.OK);
    };
    @PostMapping("/savepicture")
    @ResponseStatus(HttpStatus.OK)
    public void savePicture(@RequestParam MultipartFile picture) {
        System.out.println("Requested saving picture");
        try {
            airportService.savePicture(picture.getBytes());
        } catch (IOException e) {
            System.out.println("ERROR GET BYTES");
        }
    };

    @GetMapping("/getallpictures")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getAllPictures() {
        System.out.println("Requested all pictures");
        return new ResponseEntity<>(airportService.getAllImages(), HttpStatus.OK);
    };






    @GetMapping("/transaction")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> makeTransaction(@RequestParam String cardnum, @RequestParam String code, @RequestParam Float amount,
                                                  @RequestParam String airpacc) {
        System.out.println("Requested Transaction");
        Integer response = sockService.checkOp(cardnum, code, amount, airpacc);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
