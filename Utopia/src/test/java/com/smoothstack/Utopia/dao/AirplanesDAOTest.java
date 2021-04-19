package com.smoothstack.Utopia.dao;

import com.smoothstack.Utopia.data.flights.Airplane;
import com.smoothstack.Utopia.data.flights.AirplaneType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AirplanesDAOTest {
    private static AirplanesDAO airplanesDAO;
    @BeforeAll
    static void beforeAll() throws SQLException, ClassNotFoundException {
        airplanesDAO = new AirplanesDAO();
    }

    @AfterAll
    static void afterAll() throws SQLException {
        airplanesDAO.close();
    }

    @Test
    void airplaneFromIDTest() throws SQLException {
        //Given value for airplane of ID 1: (id: 1, type_id: 1)
        //Given value for airplane_type of ID 1: (id: 1, max_capacity: 150)
        Airplane plane = airplanesDAO.airplaneFromID(1);
        assertEquals(new Airplane(1, new AirplaneType(1, 150)), plane);
    }

    @Test
    void typeFromIDTest() throws SQLException {
        //Given value for airplane of ID 1: (id: 1, type_id: 1)
        //Given value for airplane_type of ID 1: (id: 1, max_capacity: 150)
        AirplaneType type = airplanesDAO.typeFromID(1);
        assertEquals(type, new AirplaneType(1, 150));
    }

    @Test
    void typeFromCapacityTest() throws SQLException {
        //Given value for airplane of ID 1: (id: 1, type_id: 1)
        //Given value for airplane_type of ID 1: (id: 1, max_capacity: 150)
        AirplaneType type = airplanesDAO.typeFromCapacity(150);
        assertEquals(type, new AirplaneType(1, 150));
    }

    @Test
    void insertDirectTest() {
        //TODO It'd be dirty but this one might be possible
    }

    @Test
    void insertTypeTest() {
        //TODO It'd be dirty but this one might be possible

    }

    @Test
    void getAllTest() {
        //TODO Can this even be unit tested?
    }
}