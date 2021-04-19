package com.smoothstack.Utopia.data.bookings;

import com.smoothstack.Utopia.data.flights.Flight;

import java.util.Objects;

/**
 * Relates a Flight to a Booking.
 */
public class FlightBooking {
    private static final long serialVersionUID = 4116504198562385128L;

    private Flight flight;
    private Booking booking;

    public FlightBooking(Flight flight, Booking booking) {
        this.flight = flight;
        this.booking = booking;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightBooking that = (FlightBooking) o;
        return flight.equals(that.flight) && booking.equals(that.booking);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flight, booking);
    }

    @Override
    public String toString() {
        return String.format("Booking %d has Flight %s", booking.getId(), flight.toString());
    }
}
