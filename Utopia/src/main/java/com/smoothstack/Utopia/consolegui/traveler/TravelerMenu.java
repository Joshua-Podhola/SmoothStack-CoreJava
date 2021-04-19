package com.smoothstack.Utopia.consolegui.traveler;

import com.smoothstack.Utopia.dao.BookingDAO;
import com.smoothstack.Utopia.dao.FlightsDAO;
import com.smoothstack.Utopia.data.bookings.Booking;
import com.smoothstack.Utopia.data.flights.Flight;
import com.smoothstack.Utopia.data.flights.FlightSeats;
import com.smoothstack.Utopia.util.Choices;

import java.sql.SQLException;
import java.util.ArrayList;

public class TravelerMenu {
    public static void GuestMenu() {
        try {
            BookingDAO bookingDAO = new BookingDAO();
            FlightsDAO flightsDAO = new FlightsDAO();
            System.out.println("I'm supposed to ask you about your \"Membership Number\" but that isn't even in the schema");
            switch (Choices.getNumericalChoice("Enter a selection:\n" +
                    "1) Book a flight\n" +
                    "2) Cancel a flight\n" +
                    "3) Exit", 3)) {
                case 1:
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
                    } while (selection == null);
                    FlightSeats fs = flightsDAO.getFlightSeats(selection.getId());
                    if(fs == null) {
                        System.err.println("No seating information available for that flight.");
                        return;
                    }
                    else if(fs.getBusiness() == fs.getEconomy() && fs.getEconomy() == fs.getFirst() && fs.getFirst() == 0) {
                        System.err.println("No available seats for that flight.");
                        return;
                    }
                    FlightSeats updated = new FlightSeats(fs.getFlight_id(), fs.getFirst(), fs.getBusiness(), fs.getEconomy());
                    int cc = Choices.getNumericalChoice("Which seats to book?\n" +
                            "1) First Class\n" +
                            "2) Business Class\n" +
                            "3) Economy Class", 3);
                    switch(cc) {
                        case 1:
                            if(fs.getFirst() != 0) {
                                updated.setFirst(updated.getFirst() - 1);
                            }
                            else {
                                System.err.println("No seats available.");
                                return;
                            }
                            break;
                        case 2:
                            if(fs.getBusiness() != 0) {
                                updated.setFirst(updated.getBusiness() - 1);
                            }
                            else {
                                System.err.println("No seats available.");
                                return;
                            }
                            break;
                        case 3:
                            if(fs.getEconomy() != 0) {
                                updated.setFirst(updated.getEconomy() - 1);
                            }
                            else {
                                System.err.println("No seats available.");
                                return;
                            }
                    }

                    int booking_id = bookingDAO.insertDirect(true, Choices.getStringInput("Enter your own confirmation code:"));
                    bookingDAO.insertFlightBooking(selection.getId(), booking_id);
                    bookingDAO.commit();
                    flightsDAO.commit();
                    System.out.printf("Booked! Your booking ID is %d%n", booking_id);
                    break;
                case 2:
                    Booking cancelTarget = bookingDAO.bookingFromID(Choices.getIntegerInput("What was your Booking ID?"));
                    if(cancelTarget != null) {
                        Booking up = new Booking(cancelTarget.getId(), false, cancelTarget.getConfirmationCode());
                        bookingDAO.update(cancelTarget, up);
                        System.out.println("Booking cancelled!");
                    }
                    break;
                case 3:
                    break;
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("An error has occurred. Exiting to menu...");
        }
    }
}
