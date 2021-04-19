package com.smoothstack.Utopia.data.bookings;

import java.util.Objects;

/**
 * The payment information of a booking.
 */
public class BookingPayment {
    private static final long serialVersionUID = -1265558672390542705L;

    private String stripe_id;
    private boolean refunded;

    /**
     * @param stripe_id The card number of the payment.
     * @param refunded Was this payment refunded?
     */
    public BookingPayment(String stripe_id, boolean refunded) {
        this.stripe_id = stripe_id;
        this.refunded = refunded;
    }

    /**
     * @param stripe_id The card number of the payment.
     * @param refunded Was this payment refunded? 0 if false, else true.
     */
    public BookingPayment(String stripe_id, int refunded) {
        this(stripe_id, refunded == 0);
    }

    public String getStripe_id() {
        return stripe_id;
    }

    public void setStripe_id(String stripe_id) {
        this.stripe_id = stripe_id;
    }

    public boolean isRefunded() {
        return refunded;
    }

    public void setRefunded(boolean refunded) {
        this.refunded = refunded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingPayment that = (BookingPayment) o;
        return refunded == that.refunded && stripe_id.equals(that.stripe_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stripe_id, refunded);
    }

    @Override
    public String toString() {
        return "BookingPayment{" +
                "stripe_id='" + stripe_id + '\'' +
                ", refunded=" + refunded +
                '}';
    }
}
