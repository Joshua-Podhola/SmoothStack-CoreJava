package com.smoothstack.Utopia.dao;

import com.smoothstack.Utopia.data.bookings.Booking;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class BookingDAOTest {
    static BookingDAO bookingDAO;
    @BeforeAll
    static void beforeAll() throws SQLException, ClassNotFoundException {
        bookingDAO = new BookingDAO();
    }

    @AfterAll
    static void afterAll() {

    }

    @Test
    void insertDirectTest() {
        //TODO
    }

    @Test
    void passengerConvertToTest() {
        //TODO I don't think this one can be unit tested
    }

    @Test
    void getAllPassengersTest() throws SQLException {
        assertEquals(2, bookingDAO.getAllPassengers(new Booking(1, true, "123456")).size());
    }

    @Test
    void addPassengerDirectTest() {
        //TODO
    }

    @Test
    void bookingFromIDTest() throws SQLException {
        assertEquals(1, bookingDAO.bookingFromID(1).getId());
    }

    @Test
    void passengerFromIDTest() throws SQLException {
        assertEquals(1, bookingDAO.passengerFromID(1).getId());
    }

    @Test
    void getAllTest() throws SQLException {
        assertEquals(6, bookingDAO.getAll().size());
    }

    @Test
    void updatePassengerTest() {
        //TODO
    }

    @Test
    void deletePassengerTest() {
        //TODO
    }

    @Test
    void insertFlightBookingTest() {
        //TODO
    }

    @Test
    void deleteFlightBookingTest() {
        //TODO
    }

    @Test
    void getFlightBookingsTest() {
        //TODO
    }

    @Test
    void convertToFlightBookingTest() {
        //TODO
    }

    @Test
    void flightBookingExistsTest() {
        //TODO
    }

    @Test
    void getAgentBookingsTest() {
    }
}