package com.smoothstack.Utopia.dao;

import com.smoothstack.Utopia.data.flights.Airplane;
import com.smoothstack.Utopia.data.flights.AirplaneType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AirplanesDAOTest {
    private static AirplanesDAO airplanesDAO;

    @BeforeAll
    static void beforeAll() throws SQLException, ClassNotFoundException {
        airplanesDAO = new AirplanesDAO("utopia_test");
    }

    @AfterAll
    static void afterAll() throws SQLException {
        airplanesDAO.close();
    }

    @Test
    void airplaneFromIDTest() throws SQLException {
        Airplane plane1 = airplanesDAO.airplaneFromID(1);
        Airplane plane2 = airplanesDAO.airplaneFromID(2);
        Airplane planeNull = airplanesDAO.airplaneFromID(3);
        assertEquals(1, plane1.getType().getId());
        assertEquals(2, plane2.getType().getId());
        assertNull(planeNull);
    }

    @Test
    void typeFromIDTest() throws SQLException {
        AirplaneType type = airplanesDAO.typeFromID(1);
        assertEquals(150, type.getCapacity());
        assertNull(airplanesDAO.typeFromID(4));
    }

    @Test
    void typeFromCapacityTest() throws SQLException {
        AirplaneType type = airplanesDAO.typeFromCapacity(150);
        assertEquals(150, type.getCapacity());
        assertNull(airplanesDAO.typeFromCapacity(4));
    }

    @Test
    void insertDirectTest() throws SQLException {
        int id = airplanesDAO.insertDirect(150);
        assertEquals(150, airplanesDAO.airplaneFromID(id).getType().getCapacity());
        airplanesDAO.rollback();
    }

    @Test
    void insertTypeTest() {
        //TODO
    }

    @Test
    void getAllTest() throws SQLException {
        //TODO
        ArrayList<Airplane> all = airplanesDAO.getAll();
        assertEquals(2, all.size());
    }
}