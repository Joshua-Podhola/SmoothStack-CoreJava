package com.smoothstack.Utopia.dao;

import com.smoothstack.Utopia.data.flights.Airport;
import com.smoothstack.Utopia.data.flights.Flight;
import com.smoothstack.Utopia.data.flights.Route;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FlightsDAOTest {
    static FlightsDAO flightsDAO;
    @BeforeAll
    static void beforeAll() throws SQLException, ClassNotFoundException {
        flightsDAO = new FlightsDAO();
    }

    @AfterAll
    static void afterAll() throws SQLException {
        flightsDAO.close();
    }

    @Test
    void routeFromIDTest() throws SQLException {
        //Given value: (id=1, origin_id='JFK', destination_id='PDX')
        Route route = flightsDAO.routeFromID(1);
        assertEquals(new Route(1, new Airport("JFK", "New York"), new Airport("PDX", "Portland")), route);
    }

    @Test
    void insertDirectTest() {
        //TODO
    }

    @Test
    void getAllTest() {
        //TODO
    }

    @Test
    void routeIDFromIATAsTest() throws SQLException {
        //Given value: (id=1, origin_id='JFK', destination_id='PDX')
        int id = flightsDAO.routeIDFromIATAs("JFK", "PDX");
        assertEquals(1, id);
    }

    @Test
    void insertNewRouteTest() {
        //TODO
    }

    @Test
    void getFromIDTest() throws SQLException, ClassNotFoundException {
        AirplanesDAO airplanesDAO = new AirplanesDAO();
        //Given value: (id=1, route_id=1, airplane_id=1, departure_time=2021-01-28 12:00:00, reserved_seats=30, seat_price=100.0)
        Flight flight = flightsDAO.getFromID(1);
        Flight actual = new Flight(1, flightsDAO.routeFromID(1), airplanesDAO.airplaneFromID(1), LocalDateTime.of(2021, 1, 28, 12, 0), 30, 100f);
    }
}