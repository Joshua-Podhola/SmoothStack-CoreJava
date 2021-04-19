package com.smoothstack.Utopia.data.users;

import java.io.Serializable;
import java.util.Objects;

/**
 * A User in the database system.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 763678477714112684L;


    private int id;
    private UserRole role;
    private String given_name, family_name, username, email, password, phone;

    /**
     * Constructs a new User.
     * @param id ID
     * @param role A UserRole representing the user's permission level.
     * @param given_name The user's first name.
     * @param family_name The user's surname.
     * @param username The login username of the user.
     * @param email The email address of the user.
     * @param password The password of the user.
     * @param phone The phone # of the user.
     */
    public User(int id, UserRole role, String given_name, String family_name, String username, String email, String password, String phone) {
        this.id = id;
        this.role = role;
        this.given_name = given_name;
        this.family_name = family_name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
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
        User user = (User) o;
        return id == user.id && role.equals(user.role) && given_name.equals(user.given_name) && family_name.equals(user.family_name) && username.equals(user.username) && email.equals(user.email) && password.equals(user.password) && phone.equals(user.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, given_name, family_name, username, email, password, phone);
    }

    @Override
    public String toString() {
        return String.format("%d) %s %s %s is %s at %s with number %s", id, role.getName(), given_name, family_name, username, email, phone);
    }
}
