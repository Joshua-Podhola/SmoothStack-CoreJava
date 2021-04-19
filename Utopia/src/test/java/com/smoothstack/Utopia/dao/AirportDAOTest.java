package com.smoothstack.Utopia.dao;

import com.smoothstack.Utopia.data.flights.Airport;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AirportDAOTest {
    private static AirportDAO airportDAO;

    @BeforeAll
    static void beforeAll() throws SQLException, ClassNotFoundException {
        airportDAO = new AirportDAO("utopia_test");
    }

    @AfterAll
    static void afterAll() throws SQLException {
        airportDAO.close();
    }

    @Test
    void getFromIATATest() throws SQLException {
        //Given value: EAA,Eagle
        assertEquals("Eagle", airportDAO.getFromIATA("EAA").getCity());
        //Given value: IAB,Wichita
        assertEquals("Wichita", airportDAO.getFromIATA("IAB").getCity());
        //Not in DB
        assertNull(airportDAO.getFromIATA("ZZZ"));
    }

    @Test
    void getAllTest() throws SQLException {
        ArrayList<Airport> all = airportDAO.getAll();
        assertEquals(61, all.size());
    }

    @Test
    void insertDirectTest() throws SQLException {
        airportDAO.insertDirect("QQQ", "Queam");
        assertEquals("Queam", airportDAO.getFromIATA("QQQ").getCity());
        airportDAO.rollback();
    }

    @Test
    void existsTest() throws SQLException {
        assertTrue(airportDAO.exists("EAA"));
        assertFalse(airportDAO.exists("QQQ"));
    }
}