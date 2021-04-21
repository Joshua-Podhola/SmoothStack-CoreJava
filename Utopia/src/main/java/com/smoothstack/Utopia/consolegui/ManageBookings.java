package com.smoothstack.Utopia.consolegui;

import com.smoothstack.Utopia.dao.bookings.BookingDAO;
import com.smoothstack.Utopia.dao.bookings.PassengerDAO;
import com.smoothstack.Utopia.data.bookings.Booking;
import com.smoothstack.Utopia.data.bookings.Passenger;
import com.smoothstack.Utopia.data.flights.Flight;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import static com.smoothstack.Utopia.util.Choices.*;
import static com.smoothstack.Utopia.util.Choices.getChoiceFromList;
import static com.smoothstack.Utopia.util.DatabaseConnection.getConnection;
import static java.util.Random.*;

public class ManageBookings {
    public static ArrayList<Booking> searchBookings(Connection connection) {
        try {
            BookingDAO bookingDAO = new BookingDAO(connection);
            ArrayList<String> choices = new ArrayList<>();
            choices.add("Confirmation Code");
            choices.add("Booking User/Agent Family Name");
            switch (getNumericalChoice("What search criteria to use?", choices)) {
                case 1:
                    return bookingDAO.searchByConfirmationCode(getStringInput("What is the confirmation code?"));
                case 2:
                    return bookingDAO.searchByUsername(getStringInput("What is the username?"));
            }
            return null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static void ManageBookingsMainMenu() {
        Connection connection = null;
        try {
            connection = getConnection();
            ArrayList<String> choices = new ArrayList<>();
            choices.add("Search Bookings");
            choices.add("Update a Booking");
            choices.add("Create a Booking");
            choices.add("Delete a Booking");
            choices.add("Go Back");
            ArrayList<Booking> results;
            while (true) {
                switch (getNumericalChoice("What would you like to do?", choices)) {
                    case 1:
                        results = searchBookings(connection);
                        if (results == null || results.size() == 0) {
                            System.out.println("Query produced no results.");
                        } else {
                            results.forEach(System.out::println);
                        }
                        break;
                    case 2:
                        updateBooking(connection);
                        break;
                    case 3:
                        createBooking(connection);
                        break;
                    case 4:
                        deleteBooking(connection);
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

    private static void deleteBooking(Connection connection) throws SQLException {
        if(!getBoolInput("This will also delete associated passenger info. Delete anyway?")) {
            return;
        }
        BookingDAO bookingDAO = new BookingDAO(connection);
        ArrayList<Booking> results = searchBookings(connection);
        if (results == null || results.size() == 0) {
            System.out.println("Query produced no results.");
            return;
        }
        Booking booking = getChoiceFromList("Select a booking from the list:", results);
        bookingDAO.setAssigned(booking);
        bookingDAO.delete();
        connection.commit();
        System.out.println("Flight deleted successfully.");
    }

    private static void createBooking(Connection connection) throws SQLException {
        BookingDAO bookingDAO = new BookingDAO(connection);
        PassengerDAO passengerDAO = new PassengerDAO(connection);
        Booking booking = bookingDAO.insert(true, getSaltString(25));
        if(getBoolInput("Generated new booking. Add passengers?")) {
            do {
                passengerDAO.insert(
                        booking,
                        getStringInput("Given name:"),
                        getStringInput("Family name:"),
                        getDateInput("Date of Birth:"),
                        getStringInput("Gender:"),
                        getStringInput("Address:")
                );
            } while(getBoolInput("Add another?"));
        }
        if(getBoolInput("Attach flights?")) {
            do {
                ArrayList<Flight> flights = ManageFlights.searchFlights(connection);
                if(flights != null) {
                    Flight flight = getChoiceFromList("Choose a flight:", flights);
                    bookingDAO.setAssigned(booking);
                    bookingDAO.addFlight(flight);
                    System.out.println("Added flight!");
                } else {
                    System.err.println("no");
                }
            }while (getBoolInput("Add another?"));
        }
        connection.commit();
        System.out.println("Booking created.");
    }

    private static void updateBooking(Connection connection) throws SQLException {
        BookingDAO bookingDAO = new BookingDAO(connection);
        PassengerDAO passengerDAO = new PassengerDAO(connection);
        ArrayList<Booking> results = searchBookings(connection);
        if (results == null || results.size() == 0) {
            System.out.println("Query produced no results.");
            return;
        }
        Booking booking = getChoiceFromList("Select a booking from the list:", results);
        bookingDAO.setAssigned(booking);
        System.out.printf("Booking is %s. Change it?%n", booking.isActive() ? "ACTIVE" : "INACTIVE");
        if(getBoolInput("Update passengers?")) {
            ArrayList<Passenger> passengers = passengerDAO.search(booking);
            do {
                Passenger p = getChoiceFromList("Which passenger to update?", passengers);
                passengerDAO.setAssigned(p);
                passengerDAO.updateName(
                        getStringInput("What is their new given name?"),
                        getStringInput("What is their new family name?")
                );
            } while(getBoolInput("Update another?"));
        }
        if(getBoolInput("Add new passengers?")) {
            do {
                passengerDAO.insert(
                        booking,
                        getStringInput("Given name:"),
                        getStringInput("Family name:"),
                        getDateInput("Date of Birth:"),
                        getStringInput("Gender:"),
                        getStringInput("Address:")
                );
            } while(getBoolInput("Add another?"));
        }
        if(getBoolInput("Remove passengers?")) {
            ArrayList<Passenger> passengers = passengerDAO.search(booking);
            do {
                Passenger p = getChoiceFromList("Which passenger to remove?", passengers);
                passengerDAO.setAssigned(p);
                passengerDAO.delete();
            } while(getBoolInput("Remove another?"));
        }
        if(getBoolInput("Attach flights?")) {
            do {
                ArrayList<Flight> flights = ManageFlights.searchFlights(connection);
                if(flights != null) {
                    Flight flight = getChoiceFromList("Choose a flight:", flights);
                    bookingDAO.setAssigned(booking);
                    bookingDAO.addFlight(flight);
                    System.out.println("Added flight!");
                } else {
                    System.err.println("no");
                }
            }while (getBoolInput("Add another?"));
        }
        if(getBoolInput("Remove flights?")) {
            do {
                ArrayList<Flight> flights = ManageFlights.searchFlights(connection);
                if(flights != null) {
                    Flight flight = getChoiceFromList("Choose a flight:", flights);
                    bookingDAO.setAssigned(booking);
                    bookingDAO.removeFlight(flight);
                    System.out.println("Removed flight!");
                } else {
                    System.err.println("no");
                }
            }while (getBoolInput("Remove another?"));
        }
        connection.commit();
        System.out.println("Booking updated.");
    }
}
