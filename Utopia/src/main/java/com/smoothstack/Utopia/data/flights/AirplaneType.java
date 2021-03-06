package com.smoothstack.Utopia.data.flights;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

/**
 * Information about the class of airplane.
 */
public class AirplaneType implements Serializable {
    private static final long serialVersionUID = -8002616067462750335L;
    private final int id;
    private int capacity;
    private Collection<Airplane> airplanes;

    /**
     * @param capacity The maximum number of passengers that may fly on the plane.
     */
    public AirplaneType(int id, int capacity) {
        this.capacity = capacity;
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public Collection<Airplane> getAirplanes() {
        return airplanes;
    }

    public void setAirplanes(Collection<Airplane> airplanes) {
        this.airplanes = airplanes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirplaneType that = (AirplaneType) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
