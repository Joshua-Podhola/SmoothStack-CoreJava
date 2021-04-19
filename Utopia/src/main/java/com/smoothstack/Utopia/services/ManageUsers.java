package com.smoothstack.Utopia.services;

import com.smoothstack.Utopia.dao.UserDAO;
import com.smoothstack.Utopia.data.users.User;
import com.smoothstack.Utopia.util.Choices;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManageUsers {
    public static void listAll() {
        try {
            UserDAO userDAO = new UserDAO();
            ArrayList<User> all = userDAO.getAll();
            all.forEach(System.out::println);
        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("Failed to list all users. If this issue persists, contact a system administrator.");
        }
    }

    public static void createNew() {
        try {
            UserDAO userDAO = new UserDAO();
            userDAO.insertDirect(
                    Choices.getNumericalChoice("What is the user's role?\n" +
                            "1) Admin\n" +
                            "2) Customer\n" +
                            "3) Agent", 3),
                    Choices.getStringInput("What is the user's given name?"),
                    Choices.getStringInput("What is the user's family name?"),
                    Choices.getStringInput("What is the user's username?"),
                    Choices.getStringInput("What is the user's email?"),
                    Choices.getStringInput("What is the user's password?"),
                    Choices.getStringInput("What is the user's phone number?")
            );
            userDAO.commit();
            System.out.println("Created.");
        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("Failed to insert the user. If this issue persists, contact a system administrator.");
        }
    }

    public static void updateExisting() {
        try {
            UserDAO userDAO = new UserDAO();
            int user_id = Choices.getIntegerInput("What is the ID of the user to change?");
            User old = userDAO.userFromID(user_id);
            if(old == null) {
                System.err.println("Could not find that user.");
                return;
            }
            User newValue = new User(old.getId(), old.getRole(), old.getGiven_name(), old.getFamily_name(),
                    old.getUsername(), old.getEmail(), old.getPassword(), old.getPhone());
            switch (Choices.getNumericalChoice("What would you like to change?\n" +
                    "1) User Role\n" +
                    "2) Given Name\n" +
                    "3) Family Name\n" +
                    "4) Username\n" +
                    "5) Email\n" +
                    "6) Password\n" +
                    "7) Phone #", 7)) {
                case 1:
                    newValue.setRole(userDAO.roleFromID(Choices.getNumericalChoice("What is the user's new role?\n" +
                            "1) Admin\n" +
                            "2) Customer\n" +
                            "3) Agent", 3)));
                    break;
                case 2:
                    Choices.getStringInput("What is the user's given name?");
                    break;
                case 3:
                    Choices.getStringInput("What is the user's family name?");
                    break;
                case 4:
                    Choices.getStringInput("What is the user's username?");
                    break;
                case 5:
                    Choices.getStringInput("What is the user's email?");
                    break;
                case 6:
                    Choices.getStringInput("What is the user's password?");
                    break;
                case 7:
                    Choices.getStringInput("What is the user's phone number?");
                    break;
            }
            userDAO.update(old, newValue);
            userDAO.commit();
            System.out.println("Updated.");
        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("Failed to update the user. If this issue persists, contact a system administrator.");
        }
    }

    public static void delete() {
        try {
            UserDAO userDAO = new UserDAO();
            int user_id = Choices.getIntegerInput("What is the ID of the user to delete?");
            User target = userDAO.userFromID(user_id);
            if (target == null) {
                System.err.println("Could not find that plane.");
                return;
            }
            System.out.printf("Deleting the following user:\n%s%n", target);
            userDAO.delete(target);
            userDAO.commit();
            System.out.println("Deleted.");
        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("Failed to delete the user. If this issue persists, contact a system administrator.");
        }
    }
}
