package com.smoothstack.Utopia.data.bookings;

import java.io.Serializable;
import java.util.Objects;

public class Booking implements Serializable {
    private static final long serialVersionUID = 3481071951084902685L;

    private boolean isActive;
    private String confirmationCode;
    private int id;

    /**
     * @param id The ID of the booking
     * @param isActive Is the booking active?
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

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return isActive == booking.isActive && id == booking.id && Objects.equals(confirmationCode, booking.confirmationCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isActive, confirmationCode, id);
    }

    @Override
    public String toString() {
        return String.format("%d) %s is %s", id, confirmationCode, isActive ? "ACTIVE" : "INACTIVE");
    }
}
