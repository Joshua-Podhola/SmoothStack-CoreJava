package com.smoothstack.Utopia.services;

import com.smoothstack.Utopia.dao.AirportDAO;
import com.smoothstack.Utopia.data.flights.Airport;
import com.smoothstack.Utopia.util.Choices;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManageAirports {
    public static void listAll() {
        try {
            AirportDAO airplanesDAO = new AirportDAO();
            ArrayList<Airport> all = airplanesDAO.getAll();
            all.forEach(System.out::println);
        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("Failed to list all airports. If this issue persists, contact a system administrator.");
        }
    }

    public static void createNew() {
        try {
            AirportDAO airportDAO = new AirportDAO();
            String iata = Choices.getIATAInput("What is the IATA Code?");
            if(airportDAO.exists(iata)) {
                System.err.println("That airport is already registered.");
                return;
            }
            airportDAO.insertDirect(iata, Choices.getStringInput("What is the city?"));
            airportDAO.commit();
            System.out.println("Created.");
        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("Failed to insert the airport. If this issue persists, contact a system administrator.");
        }
    }

    public static void updateExisting() {
        try {
            AirportDAO airportDAO = new AirportDAO();
            String iata = Choices.getIATAInput("What is the IATA of the airport to change?");
            Airport airport = airportDAO.getFromIATA(iata);
            if(airport == null) {
                System.err.println("Couldn't find that airport.");
            }
            airportDAO.update(airport, new Airport(iata, Choices.getStringInput("What is the new city?")));
            airportDAO.commit();
            System.out.println("Updated.");
        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("Failed to update the airport. If this issue persists, contact a system administrator.");
        }
    }

    public static void delete() {
        try {
            AirportDAO airportDAO = new AirportDAO();
            String iata = Choices.getIATAInput("What is the IATA of the airport to delete?");
            Airport target = airportDAO.getFromIATA(iata);
            if (target == null) {
                System.err.println("Could not find that airport.");
                return;
            }
            System.out.printf("Deleting the following airport:\n%s\n", target);
            airportDAO.delete(target);
            airportDAO.commit();
            System.out.println("Deleted.");
        } catch (SQLException | ClassNotFoundException throwables) {
            System.err.println("Failed to delete the airport. You must first delete all routes involving the airport.\n" +
                    " If this issue persists, contact a system administrator.");
        }
    }
}
