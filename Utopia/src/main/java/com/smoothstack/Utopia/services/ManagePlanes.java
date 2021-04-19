package com.smoothstack.Utopia.services;

import com.smoothstack.Utopia.dao.AirplanesDAO;
import com.smoothstack.Utopia.data.flights.Airplane;
import com.smoothstack.Utopia.util.Choices;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManagePlanes {
    public static void listAll() {
        try {
            AirplanesDAO airplanesDAO = new AirplanesDAO();
            ArrayList<Airplane> all = airplanesDAO.getAll();
            all.forEach(System.out::println);
        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("Failed to list all planes. If this issue persists, contact a system administrator.");
        }
    }

    public static void createNew() {
        AirplanesDAO airplanesDAO;
        try {
            airplanesDAO = new AirplanesDAO();
            airplanesDAO.insertDirect(Choices.getIntegerInput("How many does the plane hold?"));
            airplanesDAO.commit();
            System.out.println("Created.");
        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("Failed to insert the plane. If this issue persists, contact a system administrator.");
        }
    }

    public static void updateExisting() {
        AirplanesDAO airplanesDAO;
        try {
            airplanesDAO = new AirplanesDAO();
            int plane_id = Choices.getIntegerInput("What is the ID of the plane to change?");
            Airplane old = airplanesDAO.airplaneFromID(plane_id);
            if(old == null) {
                System.err.println("Could not find that plane.");
                return;
            }
            Airplane newValue = new Airplane(old.getId(), airplanesDAO.typeFromCapacity(Choices.getIntegerInput("How many does the plane hold?")));
            airplanesDAO.update(old, newValue);
            airplanesDAO.commit();
            System.out.println("Updated.");
        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("Failed to insert the flight. If this issue persists, contact a system administrator.");
        }
    }

    public static void delete() {
        AirplanesDAO airplanesDAO;
        try {
            airplanesDAO = new AirplanesDAO();
            int plane_id = Choices.getIntegerInput("What is the ID of the plane to delete?");
            Airplane target = airplanesDAO.airplaneFromID(plane_id);
            if (target == null) {
                System.err.println("Could not find that plane.");
                return;
            }
            System.out.printf("Deleting the following plane:\n%s%n", target);
            airplanesDAO.delete(target);
            airplanesDAO.commit();
            System.out.println("Deleted.");
        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("Failed to insert the flight. If this issue persists, contact a system administrator.");
        }
    }
}
