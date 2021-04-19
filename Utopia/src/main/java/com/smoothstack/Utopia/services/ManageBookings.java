package com.smoothstack.Utopia.services;

import com.smoothstack.Utopia.dao.BookingDAO;
import com.smoothstack.Utopia.dao.FlightsDAO;
import com.smoothstack.Utopia.data.bookings.Booking;
import com.smoothstack.Utopia.data.bookings.Passenger;
import com.smoothstack.Utopia.data.flights.Flight;
import com.smoothstack.Utopia.util.Choices;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManageBookings {
    public static void listAll() {
        try {
            BookingDAO bookingDAO = new BookingDAO();
            ArrayList<Booking> all = bookingDAO.getAll();
            all.forEach(System.out::println);
        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("Failed to list all bookings. If this issue persists, contact a system administrator.");
        }
    }

    public static void listPassengers() {
        try {
            BookingDAO bookingDAO = new BookingDAO();
            //I'd have asked for a confirmation code, but they are not unique
            Booking booking = bookingDAO.bookingFromID(Choices.getIntegerInput("What is the id of the booking?"));
            if(booking == null) {
                System.err.println("There is no booking with that ID.");
                return;
            }
            ArrayList<Passenger> all = bookingDAO.getAllPassengers(booking);
            all.forEach(System.out::println);
        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("Failed to list all passengers for booking. If this issue persists, contact a system administrator.");
        }
    }

    public static void createNewBooking() {
        try {
            BookingDAO bookingDAO = new BookingDAO();
            bookingDAO.insertDirect(Choices.getBoolInput("Is the booking active?"), Choices.getStringInput("What is the confirmation code?"));
            bookingDAO.commit();
            System.out.println("Created.");
        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("Failed to insert the booking. If this issue persists, contact a system administrator.");
        }
    }

    public static void createNewPassenger() {
        try {
            BookingDAO bookingDAO = new BookingDAO();
            Booking booking = bookingDAO.bookingFromID(Choices.getIntegerInput("What booking ID to add to?"));
            if(booking == null) {
                System.err.println("That booking does not exist");
                return;
            }
            bookingDAO.addPassengerDirect(booking, Choices.getStringInput("What is their given name?"),
                    Choices.getStringInput("What is their family name?"),
                    Choices.getStringInput("What is their gender?"),
                    Choices.getStringInput("What is their address?"),
                    Choices.getDateInput("What is their date of birth?"));
            bookingDAO.commit();
            System.out.println("Created.");
        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("Failed to insert the passenger. If this issue persists, contact a system administrator.");
        }
    }

    public static void updateExistingBooking() {
        try {
            BookingDAO bookingDAO = new BookingDAO();
            int booking_id = Choices.getIntegerInput("What is the ID of the booking to change?");
            Booking old = bookingDAO.bookingFromID(booking_id);
            if(old == null) {
                System.err.println("Could not find that booking.");
                return;
            }
            Booking newValue = new Booking(old.getId(), old.isActive(), old.getConfirmationCode());
            switch (Choices.getNumericalChoice("Which of the following to update?\n" +
                    "1) Is Active\n" +
                    "2) Confirmation Code", 2)) {
                case 1:
                    newValue.setActive(Choices.getBoolInput("Should the booking be active?"));
                    break;
                case 2:
                    newValue.setConfirmationCode(Choices.getStringInput("What is the confirmation code?"));
                    break;
            }
            bookingDAO.update(old, newValue);
            bookingDAO.commit();
            System.out.println("Updated.");
        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("Failed to update the booking. If this issue persists, contact a system administrator.");
        }
    }

    public static void updateExistingPassenger() {
        try {
            BookingDAO bookingDAO = new BookingDAO();
            int passenger_id = Choices.getIntegerInput("What is the ID of the passenger to change?");
            Passenger old = bookingDAO.passengerFromID(passenger_id);
            if(old == null) {
                System.err.println("Could not find that passenger.");
                return;
            }
            Passenger newValue = new Passenger(passenger_id, old.getBooking(), old.getGiven_name(),
                    old.getFamily_name(), old.getGender(), old.getAddress(), old.getDob());
            switch (Choices.getNumericalChoice("Which of the following to update?\n" +
                    "1) Booking ID\n" +
                    "2) Given Name\n" +
                    "3) Family Name\n" +
                    "4) Gender\n" +
                    "5) Address\n" +
                    "6) Date of Birth", 6)) {
                case 1:
                    Booking newBooking = bookingDAO.bookingFromID(Choices.getIntegerInput("What is the ID of the new booking?"));
                    if(newBooking == null) {
                        System.err.println("Couldn't find that booking.");
                        return;
                    }
                    newValue.setBooking(newBooking);
                    break;
                case 2:
                    newValue.setGiven_name(Choices.getStringInput("What is the new given name to use?"));
                    break;
                case 3:
                    newValue.setFamily_name(Choices.getStringInput("What is the new family name to use?"));
                    break;
                case 4:
                    newValue.setGender(Choices.getStringInput("What is the new gender to use?"));
                    break;
                case 5:
                    newValue.setAddress(Choices.getStringInput("What is the new address to use?"));
                    break;
                case 6:
                    newValue.setDob(Choices.getDateInput("What is the new Date of Birth to use?"));
                    break;
            }
            bookingDAO.updatePassenger(old, newValue);
            bookingDAO.commit();
            System.out.println("Updated.");
        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("Failed to update the passenger. If this issue persists, contact a system administrator.");
        }
    }

    public static void deleteBooking() {
        try {
            BookingDAO bookingDAO = new BookingDAO();
            int booking_id = Choices.getIntegerInput("What is the ID of the booking to delete?");
            Booking target = bookingDAO.bookingFromID(booking_id);
            if (target == null) {
                System.err.println("Could not find that booking.");
                return;
            }
            System.out.printf("Deleting the following booking:\n%s%n", target);
            bookingDAO.delete(target);
            bookingDAO.commit();
            System.out.println("Deleted.");
        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("Failed to delete the booking. Make sure to delete all passengers and detach all flights first.\n" +
                    "If this issue persists, contact a system administrator.");
        }
    }

    public static void deletePassenger() {
        try {
            BookingDAO bookingDAO = new BookingDAO();
            int passenger_id = Choices.getIntegerInput("What is the ID of the passenger to delete?");
            Passenger target = bookingDAO.passengerFromID(passenger_id);
            if (target == null) {
                System.err.println("Could not find that booking.");
                return;
            }
            System.out.printf("Deleting the following passenger:\n%s%n", target);
            bookingDAO.deletePassenger(target);
            bookingDAO.commit();
            System.out.println("Deleted.");
        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("Failed to delete the passenger. If this issue persists, contact a system administrator.");
        }
    }

    public static void attachFlight() {
        try {
            BookingDAO bookingDAO = new BookingDAO();
            FlightsDAO flightsDAO = new FlightsDAO();
            //I'd have asked for a confirmation code, but they are not unique
            Booking booking = bookingDAO.bookingFromID(Choices.getIntegerInput("What is the id of the booking?"));
            if(booking == null) {
                System.err.println("There is no booking with that ID.");
                return;
            }
            Flight flight = flightsDAO.getFromID(Choices.getIntegerInput("What is the id of the flight?"));
            if(flight == null) {
                System.err.println("There is no flight with that ID.");
                return;
            }
            if(bookingDAO.flightBookingExists(flight.getId(), booking.getId())) {
                System.err.println("Flight already attached.");
            }
            bookingDAO.insertFlightBooking(flight.getId(), booking.getId());
            bookingDAO.commit();
            System.out.println("Attached.");
        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("Failed to attach flight. If this issue persists, contact a system administrator.");
        }
    }

    public static void detachFlight() {
        try {
            BookingDAO bookingDAO = new BookingDAO();
            FlightsDAO flightsDAO = new FlightsDAO();
            //I'd have asked for a confirmation code, but they are not unique
            Booking booking = bookingDAO.bookingFromID(Choices.getIntegerInput("What is the id of the booking?"));
            if(booking == null) {
                System.err.println("There is no booking with that ID.");
                return;
            }
            Flight flight = flightsDAO.getFromID(Choices.getIntegerInput("What is the id of the flight?"));
            if(flight == null) {
                System.err.println("There is no flight with that ID.");
                return;
            }
            if(!bookingDAO.flightBookingExists(flight.getId(), booking.getId())) {
                System.err.println("Flight not attached.");
            }
            bookingDAO.deleteFlightBooking(flight.getId(), booking.getId());
            bookingDAO.commit();
            System.out.println("Detached.");
        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("Failed to detach flight. If this issue persists, contact a system administrator.");
        }
    }

    public static void listFlights() {
        try {
            BookingDAO bookingDAO = new BookingDAO();
            //I'd have asked for a confirmation code, but they are not unique
            Booking booking = bookingDAO.bookingFromID(Choices.getIntegerInput("What is the id of the booking?"));
            if(booking == null) {
                System.err.println("There is no booking with that ID.");
                return;
            }
            bookingDAO.getFlightBookings(booking.getId()).forEach(System.out::println);

        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("Failed to list all flights for booking. If this issue persists, contact a system administrator.");
        }
    }
}
