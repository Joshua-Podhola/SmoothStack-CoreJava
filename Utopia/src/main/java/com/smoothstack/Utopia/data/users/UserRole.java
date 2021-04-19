package com.smoothstack.Utopia.data.users;

import java.io.Serializable;
import java.util.Objects;

/**
 * A role that represents what the user is allowed to do.
 */
public class UserRole implements Serializable {
    private static final long serialVersionUID = 3559398348294735305L;

    private int id;
    private String name;

    public UserRole(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return id == userRole.id && name.equals(userRole.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return String.format("%d) %s", id, name);
    }
}
