package com.smoothstack.Utopia.data.bookings;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A passenger belonging to a specific booking.
 */
public class Passenger implements Serializable {
    private static final long serialVersionUID = -1695638826046622134L;
    private final int id;
    private Booking booking;
    private String given_name, family_name, gender, address;
    private LocalDate dob;

    /**
     * @param id          Their passenger ID
     * @param booking     Their booking
     * @param given_name  Their first name.
     * @param family_name Their last name.
     * @param gender      Their gender.
     * @param address     Their address.
     * @param dob         Their date of birth.
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return id == passenger.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("%s %s", given_name, family_name);
    }
}
