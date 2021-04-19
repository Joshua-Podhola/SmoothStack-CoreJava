package com.smoothstack.Utopia.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {
    Connection c;
    @Test
    void getConnectionTest() throws SQLException, ClassNotFoundException {
        c = DatabaseConnection.getConnection();
        assertTrue(c.isValid(3));
    }

    @AfterEach
    void tearDown() {
        try {
            c.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}