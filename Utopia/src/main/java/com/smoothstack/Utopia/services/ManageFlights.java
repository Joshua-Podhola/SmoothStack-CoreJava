package com.smoothstack.Utopia.services;

import com.smoothstack.Utopia.dao.AirplanesDAO;
import com.smoothstack.Utopia.dao.FlightsDAO;
import com.smoothstack.Utopia.data.flights.Airplane;
import com.smoothstack.Utopia.data.flights.Flight;
import com.smoothstack.Utopia.util.Choices;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManageFlights {
    public static void listAll() {
        try {
            FlightsDAO flights = new FlightsDAO();
            ArrayList<Flight> all = flights.getAll();
            all.forEach(System.out::println);
        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("Failed to list all flights. If this issue persists, contact a system administrator.");
        }
    }

    public static void createNew() {
        /*
        We have to prompt the user for some numeric IDs that otherwise don't make sense to ask an end user, but
        since the schema doesn't have too much identifying information in certain tables, I kinda have to
         */
        FlightsDAO flights;
        try {
            flights = new FlightsDAO();
            String origin = Choices.getIATAInput("What is the origin's ID?");
            String destination = Choices.getIATAInput("What is the destination's ID?");
            int route_id = flights.routeIDFromIATAs(origin,destination);
            if(route_id < 0) {
                System.out.printf("A new route will be created between %s and %s.%n", origin, destination);
                route_id = flights.insertNewRoute(origin, destination);
                if(route_id < 0) {
                    System.out.println("Failed to create route; one or more airports may not exist in the database.");
                    return;
                }
            }
            flights.insertDirect(
                    route_id,
                    Choices.getIntegerInput("What is the plane's ID?"),
                    Choices.getDateTimeInput("What is the time of departure?"),
                    Choices.getIntegerInput("How many seats are currently reserved?"),
                    Choices.getFloatInput("How much is a seat?")
                    );
            flights.commit();
            System.out.println("Created.");
        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("Failed to insert the flight. If this issue persists, contact a system administrator.");
        }
    }

    public static void updateExisting() {
        FlightsDAO flights;
        try {
            flights = new FlightsDAO();
            int flight_id = Choices.getIntegerInput("What is the ID of the flight to change?");
            Flight old = flights.getFromID(flight_id);
            if(old == null) {
                System.err.println("Could not find that flight.");
                return;
            }
            Flight newValue = new Flight(old.getId(), old.getRoute(), old.getAirplane(), old.getDeparture_time(), old.getReserved_seats(), old.getSeat_price());
            switch (Choices.getNumericalChoice("What would you like to change?\n" +
                    "1) Route\n" +
                    "2) Plane ID\n" +
                    "3) Time of departure\n" +
                    "4) Seats reserved\n" +
                    "5) Seat price", 5)) {
                case 1:
                    String origin = Choices.getIATAInput("What is the origin's ID?");
                    String destination = Choices.getIATAInput("What is the destination's ID?");
                    int route_id = flights.routeIDFromIATAs(origin,destination);
                    if(route_id < 0) {
                        System.out.printf("A new route will be created between %s and %s.%n", origin, destination);
                        route_id = flights.insertNewRoute(origin, destination);
                        if(route_id < 0) {
                            System.out.println("Failed to create route; one or more airports may not exist in the database.");
                            return;
                        }
                    }
                    newValue.setRoute(flights.routeFromID(route_id));
                    break;
                case 2:
                    AirplanesDAO airplanesDAO = new AirplanesDAO();
                    Airplane plane = airplanesDAO.airplaneFromID(Choices.getIntegerInput("What is the new plane's ID?"));
                    if(plane != null) {
                        newValue.setAirplane(plane);
                    }
                    else {
                        System.err.println("No plane with that ID.");
                        return;
                    }
                    break;
                case 3:
                    newValue.setDeparture_time(Choices.getDateTimeInput("What is the new departure time?"));
                    break;
                case 4:
                    newValue.setReserved_seats(Choices.getIntegerInput("How many seats are now reserved?"));
                    break;
                case 5:
                    newValue.setSeat_price(Choices.getFloatInput("What is the new seat price?"));
                    break;
            }

            flights.update(old, newValue);
            flights.commit();
            System.out.println("Updated.");
        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("Failed to insert the flight. If this issue persists, contact a system administrator.");
        }
    }

    public static void deleteFlight() {
        FlightsDAO flights;
        try {
            flights = new FlightsDAO();
            int flight_id = Choices.getIntegerInput("What is the ID of the flight to delete?");
            Flight target = flights.getFromID(flight_id);
            if (target == null) {
                System.err.println("Could not find that flight.");
                return;
            }
            System.out.printf("Deleting the following flight:\n%s%n", target.toString());
            flights.delete(target);
            flights.commit();
            System.out.println("Deleted.");
        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("Failed to insert the flight. If this issue persists, contact a system administrator.");
        }
    }
}
