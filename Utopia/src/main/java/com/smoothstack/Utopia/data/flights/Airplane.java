package com.smoothstack.Utopia.data.flights;

import java.io.Serializable;
import java.util.Objects;

/**
 * Details about a given airplane
 */
public class Airplane implements Serializable {
    private static final long serialVersionUID = -2226617601981291536L;
    private final int id;
    private AirplaneType type;
    private Route route;
    private Airplane airplane;

    /**
     * @param type The airplane type
     * @param id   The airplane id, if known
     */
    public Airplane(int id, AirplaneType type) {
        this.type = type;
        this.id = id;
    }

    public AirplaneType getType() {
        return type;
    }

    public void setType(AirplaneType type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airplane airplane = (Airplane) o;
        return id == airplane.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
