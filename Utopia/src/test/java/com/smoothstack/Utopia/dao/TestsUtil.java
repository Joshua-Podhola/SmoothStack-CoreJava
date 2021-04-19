package com.smoothstack.Utopia.dao;

import com.smoothstack.Utopia.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class TestsUtil {
    public static Connection setUpDB() throws SQLException, ClassNotFoundException {
        //In case I decide later I wanna do something here
        Connection c = DatabaseConnection.getConnection("utopia_test");
        return c;
    }
}
