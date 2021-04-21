package com.smoothstack.Utopia.data.bookings;

import com.smoothstack.Utopia.dao.bookings.PassengerDAO;
import com.smoothstack.Utopia.dao.flights.FlightDAO;
import com.smoothstack.Utopia.data.flights.Flight;
import com.smoothstack.Utopia.util.DatabaseConnection;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Objects;

public class Booking implements Serializable {
    private static final long serialVersionUID = 3481071951084902685L;
    private final int id;
    private boolean isActive;
    private String confirmationCode;
    private Collection<Flight> flights;
    private Collection<Passenger> passengers;
    private Boolean hasPayment;
    private BookingPayment payment;
    private Boolean hasBookingUser;
    private BookingUser bookingUser;
    private Boolean hasBookingAgent;
    private BookingAgent bookingAgent;
    private Boolean hasBookingGuest;
    private BookingGuest bookingGuest;

    /**
     * @param id               The ID of the booking
     * @param isActive         Is the booking active?
     * @param confirmationCode The confirmation code as a string.
     */
    public Booking(int id, boolean isActive, String confirmationCode) {
        this.id = id;
        this.isActive = isActive;
        this.confirmationCode = confirmationCode;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public int getId() {
        return id;
    }

    public Collection<Flight> getFlights() {
        return flights;
    }

    public void setFlights(Collection<Flight> flights) {
        this.flights = flights;
    }

    public Collection<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(Collection<Passenger> passengers) {
        this.passengers = passengers;
    }

    public Boolean getHasPayment() {
        return hasPayment;
    }

    public void setHasPayment(Boolean hasPayment) {
        this.hasPayment = hasPayment;
    }

    public BookingPayment getPayment() {
        return payment;
    }

    public void setPayment(BookingPayment payment) {
        this.payment = payment;
    }

    public Boolean getHasBookingUser() {
        return hasBookingUser;
    }

    public void setHasBookingUser(Boolean hasBookingUser) {
        this.hasBookingUser = hasBookingUser;
    }

    public BookingUser getBookingUser() {
        return bookingUser;
    }

    public void setBookingUser(BookingUser bookingUser) {
        this.bookingUser = bookingUser;
    }

    public Boolean getHasBookingAgent() {
        return hasBookingAgent;
    }

    public void setHasBookingAgent(Boolean hasBookingAgent) {
        this.hasBookingAgent = hasBookingAgent;
    }

    public BookingAgent getBookingAgent() {
        return bookingAgent;
    }

    public void setBookingAgent(BookingAgent bookingAgent) {
        this.bookingAgent = bookingAgent;
    }

    public Boolean getHasBookingGuest() {
        return hasBookingGuest;
    }

    public void setHasBookingGuest(Boolean hasBookingGuest) {
        this.hasBookingGuest = hasBookingGuest;
    }

    public BookingGuest getBookingGuest() {
        return bookingGuest;
    }

    public void setBookingGuest(BookingGuest bookingGuest) {
        this.bookingGuest = bookingGuest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return id == booking.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(String.format("Booking %s:%n", confirmationCode));
        try {
            PassengerDAO passengerDAO = new PassengerDAO(DatabaseConnection.getConnection());
            passengerDAO.getConnection().setReadOnly(true);
            passengers = passengerDAO.search(this);
            if(passengers != null) {
                s.append("Flights:\n");
                for (Passenger p : passengers) {
                    s.append(p.toString()).append('\n');
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            //Meh
        }
        return s.toString();
    }
}
