package com.smoothstack.Utopia.data.flights;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the route a plane takes from one airport to another.
 */
public class Route implements Serializable {
    private static final long serialVersionUID = 5265059355271718667L;

    private Airport origin, destination;
    private int id;

    /**
     * @param origin Origin of flight
     * @param destination Destination of flight
     */
    public Route(int id, Airport origin, Airport destination) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
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

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return id == route.id && origin.equals(route.origin) && destination.equals(route.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, destination, id);
    }
}
