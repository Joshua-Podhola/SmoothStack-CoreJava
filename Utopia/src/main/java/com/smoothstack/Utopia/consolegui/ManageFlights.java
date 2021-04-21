package com.smoothstack.Utopia.consolegui;

import com.smoothstack.Utopia.dao.flights.AirplaneDAO;
import com.smoothstack.Utopia.dao.flights.AirportDAO;
import com.smoothstack.Utopia.dao.flights.FlightDAO;
import com.smoothstack.Utopia.dao.flights.RouteDAO;
import com.smoothstack.Utopia.data.flights.Airplane;
import com.smoothstack.Utopia.data.flights.Airport;
import com.smoothstack.Utopia.data.flights.Flight;
import com.smoothstack.Utopia.data.flights.Route;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.smoothstack.Utopia.util.Choices.*;
import static com.smoothstack.Utopia.util.DatabaseConnection.getConnection;

public class ManageFlights {
    public static ArrayList<Flight> searchFlights(Connection connection) {
        try {
            FlightDAO flightDAO = new FlightDAO(connection);
            ArrayList<String> choices = new ArrayList<>();
            choices.add("Origin City");
            choices.add("Destination City");
            switch (getNumericalChoice("What search criteria to use?", choices)) {
                case 1:
                    return flightDAO.searchByOrigin(getStringInput("Enter a search term:"));
                case 2:
                    return flightDAO.searchByDestination(getStringInput("Enter a search term:"));
            }
            return null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static void ManageFlightsMainMenu() {
        Connection connection = null;
        try {
            connection = getConnection();
            ArrayList<String> choices = new ArrayList<>();
            choices.add("Search Flights");
            choices.add("Update a Flight");
            choices.add("Create a Flight");
            choices.add("Delete a Flight");
            choices.add("Go Back");
            ArrayList<Flight> results;
            while (true) {
                switch (getNumericalChoice("What would you like to do?", choices)) {
                    case 1:
                        results = searchFlights(connection);
                        if (results == null || results.size() == 0) {
                            System.out.println("Query produced no results.");
                        } else {
                            results.forEach(System.out::println);
                        }
                        break;
                    case 2:
                        updateFlight(connection);
                        break;
                    case 3:
                        createFlight(connection);
                        break;
                    case 4:
                        deleteFlight(connection);
                        break;
                    case 5:
                        return;
                }
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            if(connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    System.err.println("A fatal error has occurred. If this issue persists, contact an administrator.");
                    return;
                }
            }
            System.err.println("An error has occurred. Changes have not been saved.");
        }
    }

    private static void deleteFlight(Connection connection) throws SQLException {
        if(!getBoolInput("WARNING: This will also disassociate any bookings from this flight with no records left. " +
                "Proceed anyway?")) {
            return;
        }
        FlightDAO flightDAO = new FlightDAO(connection);
        ArrayList<Flight> results = searchFlights(connection);
        if (results == null || results.size() == 0) {
            System.out.println("Query produced no results.");
            return;
        }
        Flight f = getChoiceFromList("Select a flight from the list:", results);
        flightDAO.setAssigned(f);
        flightDAO.delete();
        connection.commit();
        System.out.println("Flight deleted successfully.");
    }

    private static void createFlight(Connection connection) throws SQLException {
        FlightDAO flightDAO = new FlightDAO(connection);
        AirportDAO airportDAO = new AirportDAO(connection);
        RouteDAO routeDAO = new RouteDAO(connection);
        AirplaneDAO airplaneDAO = new AirplaneDAO(connection);
        ArrayList<Airport> airports = airportDAO.search(getStringInput("Enter origin city search term:"));
        if (airports == null || airports.size() == 0) {
            System.out.println("Query produced no results.");
            return;
        }
        Airport origin = getChoiceFromList("Select the airport you'd like to use as your origin:", airports);
        airports = airportDAO.search(getStringInput("Enter destination city search term:"));
        if (airports == null || airports.size() == 0) {
            System.out.println("Query produced no results.");
            return;
        }
        Airport destination = getChoiceFromList("Select the airport you'd like to use as your destination:", airports);
        Route route = routeDAO.searchByIATAPair(origin.get_iata(), destination.get_iata());
        if(route == null) {
            if(getBoolInput("No route exists with this airport pair. Create it?")) {
                route = routeDAO.insert(origin, destination);
            }
            else return;
        }
        //TODO: Query for airplane. Could maybe add names to planes or plane type names to the schema.
        Airplane airplane = airplaneDAO.getByID(1);
        flightDAO.insert(
                route,
                airplane,
                getDateTimeInput("What is the departure time"),
                getIntegerInput("How many seats are reserved?"), //Could also initialize this to 0 to start?
                getFloatInput("What is the ticket price?")
        );
        connection.commit();
        System.out.println("Flight created.");
    }

    private static void updateFlight(Connection connection) throws SQLException {
        ArrayList<String> choices_update = new ArrayList<>();
        choices_update.add("Departure Time");
        choices_update.add("Seat Price");
        choices_update.add("Route");
        FlightDAO flightDAO = new FlightDAO(connection);
        AirportDAO airportDAO = new AirportDAO(connection);
        RouteDAO routeDAO = new RouteDAO(connection);
        ArrayList<Flight> results = searchFlights(connection);
        if (results == null || results.size() == 0) {
            System.out.println("Query produced no results.");
        } else {
            Flight f = getChoiceFromList("Select a flight from the list:", results);
            flightDAO.setAssigned(f);
            switch (getNumericalChoice("What would you like to update?", choices_update)) {
                case 1:
                    flightDAO.updateDepartureTime(getDateTimeInput("Enter new departure time:"));
                    connection.commit();
                    System.out.println("Flight updated successfully.");
                    break;
                case 2:
                    flightDAO.updateSeatPrice(getFloatInput("Enter new seat price:"));
                    connection.commit();
                    System.out.println("Flight updated successfully.");
                    break;
                case 3:
                    ArrayList<Airport> airports = airportDAO.search(getStringInput("Enter origin city search term:"));
                    if (airports == null || airports.size() == 0) {
                        System.out.println("Query produced no results.");
                        return;
                    }
                    Airport origin = getChoiceFromList("Select the airport you'd like to use as your origin:", airports);
                    airports = airportDAO.search(getStringInput("Enter destination city search term:"));
                    if (airports == null || airports.size() == 0) {
                        System.out.println("Query produced no results.");
                        break;
                    }
                    Airport destination = getChoiceFromList("Select the airport you'd like to use as your destination:", airports);
                    Route route = routeDAO.searchByIATAPair(origin.get_iata(), destination.get_iata());
                    if(route == null) {
                        if(getBoolInput("No route exists with this airport pair. Create it?")) {
                            route = routeDAO.insert(origin, destination);
                        }
                        else break;
                    }
                    flightDAO.updateRoute(route);
                    connection.commit();
                    System.out.println("Updated route successfully.");
                    break;
            }
        }
    }
}
