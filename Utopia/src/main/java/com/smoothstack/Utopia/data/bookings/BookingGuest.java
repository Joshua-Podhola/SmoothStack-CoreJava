package com.smoothstack.Utopia.data.bookings;

import java.util.Objects;

/**
 * Indicates the contact information of a non-user which booked a flight.
 */
public class BookingGuest {
    private static final long serialVersionUID = 5620874373868574182L;

    private final Booking booking;
    private String contact_email, contact_phone;

    /**
     * @param booking       The booking the guest had made.
     * @param contact_email The guest's contact email.
     * @param contact_phone The guest's contact phone number.
     */
    public BookingGuest(Booking booking, String contact_email, String contact_phone) {
        this.booking = booking;
        this.contact_email = contact_email;
        this.contact_phone = contact_phone;
    }

    public Booking getBooking() {
        return booking;
    }

    public String getContact_email() {
        return contact_email;
    }

    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingGuest that = (BookingGuest) o;
        return booking.equals(that.booking) && contact_email.equals(that.contact_email) && contact_phone.equals(that.contact_phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(booking, contact_email, contact_phone);
    }

    @Override
    public String toString() {
        return "BookingGuest{" +
                "booking=" + booking +
                ", contact_email='" + contact_email + '\'' +
                ", contact_phone='" + contact_phone + '\'' +
                '}';
    }
}
