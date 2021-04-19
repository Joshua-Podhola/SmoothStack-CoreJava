package com.smoothstack.Utopia.data.flights;

import java.io.Serializable;
import java.util.Objects;

public class FlightSeats implements Serializable {
    private static final long serialVersionUID = -1839783429576149428L;

    private int flight_id;
    private int first, business, economy;

    /**
     * Flight seats constructor
     * @param flight_id The flight
     * @param first # of first class seats free
     * @param business # of business class seats free
     * @param economy # of economy class seats free
     */
    public FlightSeats(int flight_id, int first, int business, int economy) {
        this.flight_id = flight_id;
        this.first = first;
        this.business = business;
        this.economy = economy;
    }

    public int getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(int flight_id) {
        this.flight_id = flight_id;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getBusiness() {
        return business;
    }

    public void setBusiness(int business) {
        this.business = business;
    }

    public int getEconomy() {
        return economy;
    }

    public void setEconomy(int economy) {
        this.economy = economy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightSeats that = (FlightSeats) o;
        return flight_id == that.flight_id && first == that.first && business == that.business && economy == that.economy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(flight_id, first, business, economy);
    }

    @Override
    public String toString() {
        return String.format("Seats for Flight #%d: %d First Class, %d Business Class, %d Economy Class", flight_id, first, business, economy);
    }
}
