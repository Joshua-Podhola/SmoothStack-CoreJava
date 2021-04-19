package com.smoothstack.Utopia.consolegui.agent;

import com.smoothstack.Utopia.dao.AirplanesDAO;
import com.smoothstack.Utopia.dao.FlightsDAO;
import com.smoothstack.Utopia.data.flights.Airplane;
import com.smoothstack.Utopia.data.flights.Flight;
import com.smoothstack.Utopia.data.flights.FlightSeats;
import com.smoothstack.Utopia.util.Choices;

import java.sql.SQLException;
import java.util.ArrayList;

public class AgentMenu {
    public static void EmployeeMenu() {
        try{
            FlightsDAO flightsDAO = new FlightsDAO();
            ArrayList<Flight> flights = flightsDAO.getAll();
            Flight selection;
            System.out.println("Select a flight:");
            flights.forEach(System.out::println);
            do {
                int choice = Choices.getNumericalChoice("Your choice: ", flights.size());
                selection = flightsDAO.getFromID(choice);
                if (selection == null) {
                    System.err.println("That is not a valid selection.");
                }
            } while(selection == null);

            switch (Choices.getNumericalChoice("What would you like to do with that flight?\n" +
                    "1) Update Flight Seats\n" +
                    "2) Update Flight Details\n" +
                    "3) Nothing", 3)) {
                case 1:
                    FlightSeats fs = flightsDAO.getFlightSeats(selection.getId());
                    if(fs == null) {
                        System.out.println("No initial flight seat data. Creating...");
                        flightsDAO.insertFlightSeats(selection.getId(), 0, 0, 0);
                        flightsDAO.commit();
                        fs = flightsDAO.getFlightSeats(selection.getId());
                        if(fs == null) {
                            System.err.println("Something went wrong when trying to retrieve seat data.");
                            return;
                        }
                    }
                    System.out.println(fs);
                    FlightSeats updated = new FlightSeats(fs.getFlight_id(), fs.getFirst(), fs.getBusiness(), fs.getEconomy());
                    int cc = Choices.getNumericalChoice("Which seats to add?\n" +
                            "1) First Class\n" +
                            "2) Business Class\n" +
                            "3) Economy Class", 3);
                    switch(cc) {
                        case 1:
                            updated.setFirst(updated.getFirst() + Choices.getIntegerInput("How many to add?"));
                            break;
                        case 2:
                            updated.setBusiness(updated.getBusiness() + Choices.getIntegerInput("How many to add?"));
                            break;
                        case 3:
                            updated.setEconomy(updated.getEconomy() + Choices.getIntegerInput("How many to add?"));
                            break;
                    }
                    flightsDAO.updateFlightSeats(updated.getFlight_id(), updated.getFirst(), updated.getBusiness(), updated.getEconomy());
                    flightsDAO.commit();
                    System.out.println("Added seats successfully.");
                    break;
                case 2:
                    Flight newValue = new Flight(selection.getId(), selection.getRoute(), selection.getAirplane(),
                            selection.getDeparture_time(), selection.getReserved_seats(), selection.getSeat_price());
                    switch (Choices.getNumericalChoice("What would you like to change?\n" +
                            "1) Route\n" +
                            "2) Plane ID\n" +
                            "3) Time of departure\n" +
                            "4) Seats reserved\n" +
                            "5) Seat price", 5)) {
                        case 1:
                            String origin = Choices.getIATAInput("What is the origin's ID?");
                            String destination = Choices.getIATAInput("What is the destination's ID?");
                            int route_id = flightsDAO.routeIDFromIATAs(origin,destination);
                            if(route_id < 0) {
                                System.out.printf("A new route will be created between %s and %s.%n", origin, destination);
                                route_id = flightsDAO.insertNewRoute(origin, destination);
                                if(route_id < 0) {
                                    System.out.println("Failed to create route; one or more airports may not exist in the database.");
                                    return;
                                }
                            }
                            newValue.setRoute(flightsDAO.routeFromID(route_id));
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

                    flightsDAO.update(selection, newValue);
                    flightsDAO.commit();
                    System.out.println("Updated.");
                    break;
                case 3:
                    break;
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("An unrecoverable error has occurred. Exiting to main menu...");
        }
    }
}
