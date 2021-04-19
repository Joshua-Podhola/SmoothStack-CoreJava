package com.smoothstack.Utopia.data.bookings;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A passenger belonging to a specific booking.
 */
public class Passenger implements Serializable {
    private static final long serialVersionUID = -1695638826046622134L;

    private Booking booking;
    private String given_name, family_name, gender, address;
    private LocalDate dob;
    private int id;

    /**
     * @param id Their passenger ID
     * @param booking What booking they are a part of.
     * @param given_name Their first name.
     * @param family_name Their last name.
     * @param gender Their gender.
     * @param address Their address.
     * @param dob Their date of birth.
     */
    public Passenger(int id, Booking booking, String given_name, String family_name, String gender, String address, LocalDate dob) {
        this.id = id;
        this.booking = booking;
        this.given_name = given_name;
        this.family_name = family_name;
        this.gender = gender;
        this.address = address;
        this.dob = dob;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
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
        Passenger passenger = (Passenger) o;
        return id == passenger.id && booking.equals(passenger.booking) && given_name.equals(passenger.given_name) && family_name.equals(passenger.family_name) && gender.equals(passenger.gender) && address.equals(passenger.address) && dob.equals(passenger.dob);
    }

    @Override
    public int hashCode() {
        return Objects.hash(booking, given_name, family_name, gender, address, dob, id);
    }

    @Override
    public String toString() {
        return String.format("%d) %s %s, %s, from %s, born %s, party of booking %d",
                id, given_name, family_name, gender, address, dob.toString(), booking.getId());
    }
}
