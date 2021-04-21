package com.smoothstack.Utopia.data.users;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

/**
 * A role that represents what the user is allowed to do.
 */
public class UserRole implements Serializable {
    private static final long serialVersionUID = 3559398348294735305L;

    private final int id;
    private String name;
    private Collection<User> users;

    public UserRole(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return id == userRole.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
