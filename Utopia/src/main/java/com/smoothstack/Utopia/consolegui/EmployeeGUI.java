package com.smoothstack.Utopia.consolegui;

import com.smoothstack.Utopia.dao.flights.FlightDAO;
import com.smoothstack.Utopia.dao.flights.FlightSeatsDAO;
import com.smoothstack.Utopia.data.flights.Flight;
import com.smoothstack.Utopia.data.flights.FlightSeats;
import com.smoothstack.Utopia.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.smoothstack.Utopia.util.Choices.*;

public class EmployeeGUI {
    public static void EmployeeMainMenu() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Update Departure Time");
        options.add("Add Seats");
        options.add("Go back");
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
            FlightDAO flightDAO = new FlightDAO(connection);
            FlightSeatsDAO flightSeatsDAO = new FlightSeatsDAO(connection);
            System.out.println("Managing flights. Search your flight:");
            ArrayList<Flight> flights = ManageFlights.searchFlights(connection);
            if(flights != null) {
                Flight flight = getChoiceFromList("Select your flight", flights);
                while(true) {
                    switch (getNumericalChoice("What would you like to do?", options)) {
                        case 1:
                            flightDAO.setAssigned(flight);
                            flightDAO.updateDepartureTime(getDateTimeInput("Insert new departure time:"));
                        case 2:
                            FlightSeats fs = flightSeatsDAO.getByID(flight.getId());
                            if(fs == null) {
                                System.out.println("No existing seat information for flight. Creating...");
                                flightSeatsDAO.insert(
                                        flight,
                                        getIntegerInput("How many economy class seats?"),
                                        getIntegerInput("How many first class seats?"),
                                        getIntegerInput("How many business class seats?")
                                );
                            } else {
                                flightSeatsDAO.setAssigned(fs);
                                flightSeatsDAO.update(
                                        getIntegerInput("How many economy class seats?"),
                                        getIntegerInput("How many first class seats?"),
                                        getIntegerInput("How many business class seats?")
                                );
                            }
                        case 3:
                            return;
                    }
                }

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
