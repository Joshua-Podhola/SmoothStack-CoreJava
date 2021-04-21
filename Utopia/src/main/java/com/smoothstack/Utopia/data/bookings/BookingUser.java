package com.smoothstack.Utopia.data.bookings;

import com.smoothstack.Utopia.data.users.User;

import java.util.Objects;

/**
 * Indicates a booking which was made by an existing user.
 */
public class BookingUser {
    private static final long serialVersionUID = -1769398313484158672L;

    private final Booking booking;
    private final User user;

    /**
     * @param booking The booking the user made.
     * @param user    The user that made the booking.
     */
    public BookingUser(Booking booking, User user) {
        this.booking = booking;
        this.user = user;
    }

    public Booking getBooking() {
        return booking;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingUser that = (BookingUser) o;
        return booking.equals(that.booking) && user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(booking, user);
    }

    @Override
    public String toString() {
        return "BookingUser{" +
                "booking=" + booking +
                ", user=" + user +
                '}';
    }
}
