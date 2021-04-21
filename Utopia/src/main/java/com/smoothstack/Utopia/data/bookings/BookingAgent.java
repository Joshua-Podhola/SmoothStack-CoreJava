package com.smoothstack.Utopia.data.bookings;

import com.smoothstack.Utopia.data.users.User;

import java.io.Serializable;
import java.util.Objects;

/**
 * Indicates that a booking was made by a booking agent, and indicates which one.
 * If a BookingAgent could not be found, the flight was not booked by a booking agent.
 */
public class BookingAgent implements Serializable {
    private static final long serialVersionUID = -497623075648943439L;

    private final Booking booking;
    private final User agent;

    /**
     * @param booking The booking the agent made
     * @param agent   The agent that made the booking
     */
    public BookingAgent(Booking booking, User agent) {
        this.booking = booking;
        this.agent = agent;
    }

    public Booking getBooking() {
        return booking;
    }

    public User getAgent() {
        return agent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingAgent that = (BookingAgent) o;
        return booking.equals(that.booking) && agent.equals(that.agent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(booking, agent);
    }

    @Override
    public String toString() {
        return "BookingAgent{" +
                "booking=" + booking +
                ", agent=" + agent +
                '}';
    }
}
