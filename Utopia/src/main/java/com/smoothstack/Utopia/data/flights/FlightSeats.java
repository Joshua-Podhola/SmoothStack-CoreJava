package com.smoothstack.Utopia.data.flights;

import java.io.Serializable;
import java.util.Objects;

public class FlightSeats implements Serializable {
    private static final long serialVersionUID = -1839783429576149428L;

    private final Flight flight;
    private int first, business, economy;

    /**
     * Flight seats constructor
     *
     * @param flight   The flight the seats are for
     * @param first    # of first class seats free
     * @param business # of business class seats free
     * @param economy  # of economy class seats free
     */
    public FlightSeats(Flight flight, int first, int business, int economy) {
        assert (flight != null);
        this.flight = flight;
        this.first = first;
        this.business = business;
        this.economy = economy;
    }

    public Flight getFlight() {
        return flight;
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
        return first == that.first && business == that.business && economy == that.economy && flight.equals(that.flight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flight, first, business, economy);
    }
}
