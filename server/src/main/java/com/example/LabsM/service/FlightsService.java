package com.example.LabsM.service;

import com.example.LabsM.entity.*;
import com.example.LabsM.jpa.*;
import com.example.LabsM.jpa.model.AirportModel;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
        Integer totam = 0;
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
            Integer origin = a.getOrigin();
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



                        if(origin == -1) {
                            direction *= -1;
                            progress = 1000 - progress;
                            String[] words = dirstring.split(" ");
                            Collections.reverse(Arrays.asList(words));
                            dirstring = String.join(" ", words);
                            if(direction == 1) {
                                Integer newnum = a.getNumber() + 1;
                                fnstring = a.getAirline().getCode() + ' ' + newnum.toString();
                            } else {
                                Integer newnum = a.getNumber();
                                fnstring = a.getAirline().getCode() + ' ' + newnum.toString();
                            }
                        }


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
        totam = result.size();
        System.out.println("Sent " + totam.toString() + " active flights");
        return result;
    }

    public static String incrementNumbers(String input) {
        StringBuilder result = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                int num = Character.getNumericValue(c);
                if (num == 7) {
                    result.append(1);
                } else {
                    result.append((num % 7) + 1);
                }
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    public List<Object> getAllDepartures () {
        List<Object> response = new ArrayList<>();

        List<TimetableRecord> result = new ArrayList<>();
        List<TimetableRecord> finalresult = new ArrayList<>();
        List<TimetableRecord> resultar = new ArrayList<>();
        List<Flight> origflights = new ArrayList<>();
        List<Flight> origflights0 = dbService.getAllFlights();
        LocalTime currentTime = LocalTime.now();
        Integer currentHour = currentTime.getHour();
        Integer currentMinute = currentTime.getMinute();
        Integer speed = 900;
        Float homecoord1 = dbService.getAllAirportsWithId().get(0).getCoord1();
        Float homecoord2 = dbService.getAllAirportsWithId().get(0).getCoord2();
        String homecode = dbService.getAllAirportsWithId().get(0).getCountry();
        LocalDate currentDate = LocalDate.now();
        Integer currentDay = currentDate.getDayOfWeek().getValue();

        origflights0.forEach(a -> {
            Integer ordepHours = Integer.parseInt(a.getTime().split(":")[0]);
            Integer ordepMinutes = Integer.parseInt(a.getTime().split(":")[1]);
            String airport = a.getDestination().getCountry();
            Integer airportId = dbService.getAirportId(airport);
            Float destcoord1 = dbService.getAirportWithId(airportId).getCoord1();
            Float destcoord2 = dbService.getAirportWithId(airportId).getCoord2();
            Double totaldistance0 = calculateDistance(homecoord1.doubleValue(),
                    homecoord2.doubleValue(), destcoord1.doubleValue(), destcoord2.doubleValue());
            Float totaldistance = totaldistance0.floatValue();
            Float flightTimeHours = totaldistance / speed;
            Integer flHours = (int)Math.floor(flightTimeHours);
            Integer flMinutes = Math.round((flightTimeHours - flHours) * 60);
            Integer newDepHour = ordepHours + flHours + 2;
            Integer newDepMinute = ordepMinutes + flMinutes;
            Integer daysparam1 = 0;
            Integer daysparam1_5 = 0;
            Integer daysparam2 = 0;

            if(newDepMinute > 59) {
                newDepMinute -= 60;
                newDepHour ++;
            };
            if(newDepHour > 23) {
                newDepHour -= 24;
                daysparam1_5 = 1;
                daysparam1 = 1;
                daysparam2 = 1;
            };

            Integer orarh = newDepHour - 2;
            if(orarh < 0) {
                orarh += 24;
                daysparam1 = 0;
            }
            Integer orarm = newDepMinute;
            Integer narh = newDepHour + flHours;
            Integer narm = newDepMinute + flMinutes;
            if(narm > 59) {
                narm -= 60;
                narh ++;
            };
            if(narh > 23) {
                narh -= 24;
                daysparam2 = daysparam1_5 + 1;
            };

            String dir1 = dbService.getAllAirportsWithId().get(0).getCountry() + " -> " + a.getDestination().getCountry();
            String dir2 = a.getDestination().getCountry() + " -> " + dbService.getAllAirportsWithId().get(0).getCountry();

            TimetableRecord rec1 = new TimetableRecord();
            TimetableRecord rec2 = new TimetableRecord();
            TimetableRecord recar1 = new TimetableRecord();
            TimetableRecord recar2 = new TimetableRecord();

            if(a.getOrigin() > 0) {
                rec1.setLogo(a.getAirline().getLogo());
                rec1.setNumber(a.getAirline().getCode() + a.getNumber().toString());
                rec1.setDirection(dir1);
                rec1.setAirplane(a.getAirplane());
                rec1.setTime(a.getTime());

                rec2.setLogo(a.getAirline().getLogo());
                Integer newnum = a.getNumber() + 1;
                rec2.setNumber(a.getAirline().getCode() + newnum.toString());
                rec2.setDirection(dir2);
                rec2.setAirplane(a.getAirplane());
                String newdepmn = newDepMinute.toString();
                if(newdepmn.length() < 2) newdepmn = '0' + newdepmn;
                String newdephn = newDepHour.toString();
                if(newdephn.length() < 2) newdephn = '0' + newdephn;
                rec2.setTime(newdephn + ':' + newdepmn);

                recar1.setLogo(a.getAirline().getLogo());
                recar1.setNumber(a.getAirline().getCode() + a.getNumber().toString());
                recar1.setDirection(dir1);
                recar1.setAirplane(a.getAirplane());
                String norarh = orarh.toString();
                if(norarh.length() < 2) norarh = '0' + norarh;
                String norarm = orarm.toString();
                if(norarm.length() < 2) norarm = '0' + norarm;
                recar1.setTime(norarh + ':' + norarm);

                recar2.setLogo(a.getAirline().getLogo());
                Integer newnum1 = a.getNumber() + 1;
                recar2.setNumber(a.getAirline().getCode() + newnum1.toString());
                recar2.setDirection(dir2);
                recar2.setAirplane(a.getAirplane());
                String nnarh = narh.toString();
                if(nnarh.length() < 2) nnarh = '0' + nnarh;
                String nnarm = narm.toString();
                if(nnarm.length() < 2) nnarm = '0' + nnarm;
                recar2.setTime(nnarh + ':' + nnarm);

            } else {
                rec1.setLogo(a.getAirline().getLogo());
                rec1.setNumber(a.getAirline().getCode() + a.getNumber().toString());
                rec1.setDirection(dir2);
                rec1.setAirplane(a.getAirplane());
                rec1.setTime(a.getTime());

                rec2.setLogo(a.getAirline().getLogo());
                Integer newnum = a.getNumber() + 1;
                rec2.setNumber(a.getAirline().getCode() + newnum.toString());
                rec2.setDirection(dir1);
                rec2.setAirplane(a.getAirplane());
                String newdephn = newDepHour.toString();
                if(newdephn.length() < 2) newdephn = '0' + newdephn;
                String newdepmn = newDepMinute.toString();
                if(newdepmn.length() < 2) newdepmn = '0' + newdepmn;
                rec2.setTime(newdephn + ':' + newdepmn);

                recar1.setLogo(a.getAirline().getLogo());
                recar1.setNumber(a.getAirline().getCode() + a.getNumber().toString());
                recar1.setDirection(dir2);
                recar1.setAirplane(a.getAirplane());
                String norarh = orarh.toString();
                if(norarh.length() < 2) norarh = '0' + norarh;
                String norarm = orarm.toString();
                if(norarm.length() < 2) norarm = '0' + norarm;
                recar1.setTime(norarh + ':' + norarm);

                recar2.setLogo(a.getAirline().getLogo());
                Integer newnum1 = a.getNumber() + 1;
                recar2.setNumber(a.getAirline().getCode() + newnum1.toString());
                recar2.setDirection(dir1);
                recar2.setAirplane(a.getAirplane());
                String nnarh = narh.toString();
                if(nnarh.length() < 2) nnarh = '0' + nnarh;
                String nnarm = narm.toString();
                if(nnarm.length() < 2) nnarm = '0' + nnarm;
                recar2.setTime(nnarh + ':' + nnarm);

            }

            String newdays1 = a.getDays();
            String newdays2 = a.getDays();
            if(daysparam1 > 0) {
                newdays1 = incrementNumbers(newdays1);
//                System.out.println("1: ");
//                System.out.println(a.getAirline().getCode() + a.getNumber());
            }
            if(daysparam2 > 0) {
//                System.out.println("2: ");
                for(int k = 0; k < daysparam2; k++) {
                    newdays2 = incrementNumbers(newdays2);
//                    System.out.println(a.getAirline().getCode() + a.getNumber());
                }
            }

            rec1.setDays(a.getDays());
            rec2.setDays(a.getDays());
            recar1.setDays(newdays1);
            recar2.setDays(newdays2);
            result.add(rec1);
            result.add(rec2);
            resultar.add(recar1);
            resultar.add(recar2);

//            if(a.getNumber() == 4867) {
//                System.out.println(newdays1);
//                System.out.println(newdays2);
//                System.out.println(daysparam1);
//                System.out.println(daysparam1_5);
//                System.out.println(daysparam2);
//            }

        });

        List<TimetableRecord> homedeps = new ArrayList<>();
        List<TimetableRecord> homearrs = new ArrayList<>();

        result.forEach(a -> {
            Integer flHours = Integer.parseInt(a.getTime().split(":")[0]);
            Integer flMinutes = Integer.parseInt(a.getTime().split(":")[1]);
            Integer param = 0;
            if(flHours < currentHour) param = -1;
            else if (flHours > currentHour) param = 1;
            else if (flMinutes < currentMinute) param = -1;
            else  param = 1;
            if(param < 0) {
                Integer newh = flHours + 24;
                a.setTime(newh.toString() + ':' + a.getTime().split(":")[1]);
            };
            if (param > 0) {
                if(a.getDays().contains(currentDay.toString())){
                    finalresult.add(a);
                    if(a.getDirection().contains(homecode + " -> ")) {
                        homedeps.add(a);
                    }
                };
            } else {
                Integer newday = currentDay + 1;
                if (newday > 7) newday -= 7;
                if(a.getDays().contains(newday.toString())){
                    finalresult.add(a);
                    if(a.getDirection().contains(homecode + " -> ")) {
                        homedeps.add(a);
                    }
                };
            };
        });

        List<TimetableRecord> finalarrs = new ArrayList<>();

        resultar.forEach(a -> {
            Integer flHours = Integer.parseInt(a.getTime().split(":")[0]);
            Integer flMinutes = Integer.parseInt(a.getTime().split(":")[1]);
            Integer param = 0;
            if(flHours < currentHour) param = -1;
            else if (flHours > currentHour) param = 1;
            else if (flMinutes < currentMinute) param = -1;
            else  param = 1;
            if(param < 0) {
                Integer newh = flHours + 24;
                a.setTime(newh.toString() + ':' + a.getTime().split(":")[1]);
            };
            if (param > 0) {
                if(a.getDays().contains(currentDay.toString())){
                    finalarrs.add(a);
                    if(a.getDirection().contains(" -> " + homecode)) {
                        homearrs.add(a);
                    }
                };
            } else {
                Integer newday = currentDay + 1;
                if (newday > 7) newday -= 7;
                if(a.getDays().contains(newday.toString())){
                    finalarrs.add(a);
                    if(a.getDirection().contains(" -> " + homecode)) {
                        homearrs.add(a);
                    }
                };
            };
        });

        Collections.sort(finalresult, new Comparator<TimetableRecord>() {
            public int compare(TimetableRecord o1, TimetableRecord o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        });
        Collections.sort(homedeps, new Comparator<TimetableRecord>() {
            public int compare(TimetableRecord o1, TimetableRecord o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        });
        Collections.sort(finalarrs, new Comparator<TimetableRecord>() {
            public int compare(TimetableRecord o1, TimetableRecord o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        });
        Collections.sort(homearrs, new Comparator<TimetableRecord>() {
            public int compare(TimetableRecord o1, TimetableRecord o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        });

        Integer tablenum = 20;

        List<TimetableRecord> finalalldeps = finalresult.subList(0, tablenum);
        List<TimetableRecord> finalhomedeps = homedeps.subList(0, tablenum);
        List<TimetableRecord> finalallarrs = finalarrs.subList(0, tablenum);
        List<TimetableRecord> finalhomearrs = homearrs.subList(0, tablenum);

        finalalldeps.forEach(a -> {
            Integer flHours = Integer.parseInt(a.getTime().split(":")[0]);
            if (flHours > 23) flHours -= 24;
            String newfh = flHours.toString();
            if (newfh.length() < 2) newfh = '0' + newfh;
            a.setTime(newfh + ':' + a.getTime().split(":")[1]);
        });
        finalhomedeps.forEach(a -> {
            Integer flHours = Integer.parseInt(a.getTime().split(":")[0]);
            if (flHours > 23) flHours -= 24;
            String newfh = flHours.toString();
            if (newfh.length() < 2) newfh = '0' + newfh;
            a.setTime(newfh + ':' + a.getTime().split(":")[1]);
        });
        finalallarrs.forEach(a -> {
            Integer flHours = Integer.parseInt(a.getTime().split(":")[0]);
            if (flHours > 23) flHours -= 24;
            String newfh = flHours.toString();
            if (newfh.length() < 2) newfh = '0' + newfh;
            a.setTime(newfh + ':' + a.getTime().split(":")[1]);
        });
        finalhomearrs.forEach(a -> {
            Integer flHours = Integer.parseInt(a.getTime().split(":")[0]);
            if (flHours > 23) flHours -= 24;
            String newfh = flHours.toString();
            if (newfh.length() < 2) newfh = '0' + newfh;
            a.setTime(newfh + ':' + a.getTime().split(":")[1]);
        });

        response.add(finalalldeps);
        response.add(finalhomedeps);
        response.add(finalallarrs);
        response.add(finalhomearrs);

        return response;
    }

    public List<Search> getForBooking(String destination, Integer amount, String date, Integer origin) {

        LocalDate wdate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        DayOfWeek dayOfWeek = wdate.getDayOfWeek();
        Integer flightday = dayOfWeek.getValue();
        Float homecoord1 = dbService.getAllAirportsWithId().get(0).getCoord1();
        Float homecoord2 = dbService.getAllAirportsWithId().get(0).getCoord2();
        String homecode = dbService.getAllAirportsWithId().get(0).getCountry();
        Integer speed = 900;

        List<Flight> response = new ArrayList<>();

        List<Flight> allflights = dbService.getAllFlights();
        List<Search> goodflights = new ArrayList<>();

        allflights.forEach(a -> {
            if(a.getDestination().getCountry().equals(destination)) {

////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                    Integer ordepHours = Integer.parseInt(a.getTime().split(":")[0]);
                    Integer ordepMinutes = Integer.parseInt(a.getTime().split(":")[1]);
                    String airport = a.getDestination().getCountry();
                    Integer airportId = dbService.getAirportId(airport);
                    Float destcoord1 = dbService.getAirportWithId(airportId).getCoord1();
                    Float destcoord2 = dbService.getAirportWithId(airportId).getCoord2();
                    Double totaldistance0 = calculateDistance(homecoord1.doubleValue(),
                            homecoord2.doubleValue(), destcoord1.doubleValue(), destcoord2.doubleValue());
                    Float totaldistance = totaldistance0.floatValue();
                    Float flightTimeHours = totaldistance / speed;
                    Integer flHours = (int)Math.floor(flightTimeHours);
                    Integer flMinutes = Math.round((flightTimeHours - flHours) * 60);
                    Integer newDepHour = ordepHours + flHours + 2;
                    Integer newDepMinute = ordepMinutes + flMinutes;
                    Integer daysparam1 = 0;
                    Integer daysparam1_5 = 0;
                    Integer daysparam2 = 0;

                    if(newDepMinute > 59) {
                        newDepMinute -= 60;
                        newDepHour ++;
                    };
                    if(newDepHour > 23) {
                        newDepHour -= 24;
                        daysparam1_5 = 1;
                        daysparam1 = 1;
                        daysparam2 = 1;
                    };

                    Integer orarh = newDepHour - 2;
                    if(orarh < 0) {
                        orarh += 24;
                        daysparam1 = 0;
                    }
                    Integer orarm = newDepMinute;
                    Integer narh = newDepHour + flHours;
                    Integer narm = newDepMinute + flMinutes;
                    if(narm > 59) {
                        narm -= 60;
                        narh ++;
                    };
                    if(narh > 23) {
                        narh -= 24;
                        daysparam2 = daysparam1_5 + 1;
                    };

////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                if(origin > 0) {
                    if(a.getOrigin() > 0) {
                        if(a.getDays().contains(flightday.toString())) {
                            String newflh = flHours.toString();
                            if(newflh.length() < 2) newflh = '0' + newflh;
                            String newflm = flMinutes.toString();
                            if(newflm.length() < 2) newflm = '0' + newflm;
                            Search b = new Search(a.getDestination().getName(), homecode + " -> " + a.getDestination().getCountry(),
                                    a.getAirline().getName(), a.getAirline().getCode() + a.getNumber().toString(),
                                    a.getTime(), a.getAirline().getLogo(),
                                    (float)(Math.round(a.getPrice() * amount * 100.0) / 100.0),
                                    newflh + ':' + newflm, a.getAirplane(), date);
                            goodflights.add(b);
                        }
                    } else {
                        if(daysparam1_5 > 0) {
                            a.setDays(incrementNumbers(a.getDays()));
                        }
                        Integer newnum = a.getNumber() + 1;
                        if(a.getDays().contains(flightday.toString())) {
                            String newflh = flHours.toString();
                            if(newflh.length() < 2) newflh = '0' + newflh;
                            String newflm = flMinutes.toString();
                            if(newflm.length() < 2) newflm = '0' + newflm;
                            String newdeph = newDepHour.toString();
                            if(newdeph.length() < 2) newdeph = '0' + newdeph;
                            String newdepm = newDepMinute.toString();
                            if(newdepm.length() < 2) newdepm = '0' + newdepm;
                            Search b = new Search(a.getDestination().getName(), homecode + " -> " + a.getDestination().getCountry(),
                                    a.getAirline().getName(), a.getAirline().getCode() + newnum.toString(),
                                    newdeph + ':' + newdepm, a.getAirline().getLogo(),
                                    (float)(Math.round(a.getPrice() * amount * 100.0) / 100.0),
                                    newflh + ':' + newflm, a.getAirplane(), date);
                            goodflights.add(b);
                        }
                    }
                } else {
                    if(a.getOrigin() > 0) {
                        if(daysparam1_5 > 0) {
                            a.setDays(incrementNumbers(a.getDays()));
                        }
                        Integer newnum = a.getNumber() + 1;
                        if(a.getDays().contains(flightday.toString())) {
                            String newflh = flHours.toString();
                            if(newflh.length() < 2) newflh = '0' + newflh;
                            String newflm = flMinutes.toString();
                            if(newflm.length() < 2) newflm = '0' + newflm;
                            String newdeph = newDepHour.toString();
                            if(newdeph.length() < 2) newdeph = '0' + newdeph;
                            String newdepm = newDepMinute.toString();
                            if(newdepm.length() < 2) newdepm = '0' + newdepm;
                            Search b = new Search(a.getDestination().getName(), a.getDestination().getCountry() + " -> " + homecode,
                                    a.getAirline().getName(), a.getAirline().getCode() + newnum.toString(),
                                    newdeph + ':' + newdepm, a.getAirline().getLogo(),
                                    (float)(Math.round(a.getPrice() * amount * 100.0) / 100.0),
                                    newflh + ':' + newflm, a.getAirplane(), date);
                            goodflights.add(b);
                        }
                    } else {
                        if(a.getDays().contains(flightday.toString())) {
                            String newflh = flHours.toString();
                            if(newflh.length() < 2) newflh = '0' + newflh;
                            String newflm = flMinutes.toString();
                            if(newflm.length() < 2) newflm = '0' + newflm;
                            Search b = new Search(a.getDestination().getName(), a.getDestination().getCountry() + " -> " + homecode,
                                    a.getAirline().getName(), a.getAirline().getCode() + a.getNumber().toString(),
                                    a.getTime(), a.getAirline().getLogo(), (float)(Math.round(a.getPrice() * amount * 100.0) / 100.0),
                                    newflh + ':' + newflm, a.getAirplane(), date);
                            goodflights.add(b);
                        }
                    }
                }
            }
        });

        Collections.sort(goodflights, new Comparator<Search>() {
            public int compare(Search o1, Search o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        });

        return goodflights;
    }
}
