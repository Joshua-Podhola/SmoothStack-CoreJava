package com.smoothstack.Utopia.data.flights;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents an airport origin or destination.
 */
public class Airport implements Serializable {
    private static final long serialVersionUID = -8940316279613031113L;

    private final String iata_id;
    private String city;
    private Collection<Route> routes;

    /**
     * @param iata_id 3-letter IATA airport identifier
     * @param city    City of which the airport resides in
     */
    public Airport(String iata_id, String city) {
        assert (iata_id != null);
        this.iata_id = iata_id;
        this.city = city;
    }

    public String get_iata() {
        return iata_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Collection<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(Collection<Route> routes) {
        this.routes = routes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport = (Airport) o;
        return iata_id.equals(airport.iata_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iata_id);
    }

    @Override
    public String toString() {
        return String.format("%s: %s", iata_id, city);
    }
}
