package com.smoothstack.Utopia.data.flights;

import java.io.Serializable;
import java.util.Objects;

/**
 * Details about a given airplane
 */
public class Airplane implements Serializable {
    private static final long serialVersionUID = -2226617601981291536L;

    private AirplaneType type;
    private int id;

    /**
     * @param type The airplane type
     * @param id The airplane id, if known
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

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airplane airplane = (Airplane) o;
        return id == airplane.id && Objects.equals(type, airplane.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, id);
    }

    @Override
    public String toString() {
        return String.format("%d) Type %d (Carries %d)", id, type.getId(), type.getCapacity());
    }
}
