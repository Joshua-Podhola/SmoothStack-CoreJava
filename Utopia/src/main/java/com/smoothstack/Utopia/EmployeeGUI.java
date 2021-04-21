package com.smoothstack.Utopia;

import com.smoothstack.Utopia.consolegui.AdministratorGUI;
import com.smoothstack.Utopia.dao.flights.FlightDAO;
import com.smoothstack.Utopia.data.flights.Flight;
import com.smoothstack.Utopia.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.smoothstack.Utopia.util.Choices.getNumericalChoice;
import static com.smoothstack.Utopia.util.Choices.getStringInput;

public class EmployeeGUI {
    public static void EmployeeMainMenu() {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
            FlightDAO flightDAO = new FlightDAO(connection);
            while (true) {
                System.out.println("Managing flights. Search your flight:");
                ArrayList<Flight> flights = flightDAO.searchByOrigin(getStringInput("What is the origin city?"));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            if(connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    System.err.println("A fatal error has occurred. If this issue persists, contact an administrator.");
                    return;
                }
            }
            System.err.println("An error has occurred. Changes have not been saved.");
        }
    }
}
