package com.smoothstack.Utopia.data.flights;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents the route a plane takes from one airport to another.
 */
public class Route implements Serializable {
    private static final long serialVersionUID = 5265059355271718667L;
    private final int id;
    private Airport origin, destination;
    private Collection<Flight> flights;

    /**
     * @param id The ID of the route
     */
    public Route(int id, Airport origin, Airport destination) {
        this.origin = origin;
        this.destination = destination;
        this.id = id;
    }

    public Airport getOrigin() {
        return origin;
    }

    public void setOrigin(Airport origin) {
        this.origin = origin;
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public int getId() {
        return id;
    }

    public Collection<Flight> getFlights() {
        return flights;
    }

    public void setFlights(Collection<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return id == route.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
