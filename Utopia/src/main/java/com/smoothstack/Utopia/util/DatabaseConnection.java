package com.smoothstack.Utopia.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Boolean driverLoaded = false;
    //I'm only doing this because nobody else but me can connect to the database anyway. No, I wouldn't otherwise do this.
    private static final String DATABASE_URL = "jdbc:mysql://localhost/%s?user=utopia&password=NAg6K0V2guDxAXQC";

    /**
     * Get a database connection.
     * @throws ClassNotFoundException The MySQL JDBC driver is likely not installed.
     * @throws SQLException Failed to connect to the MySQL database.
     * @return Configured database connection.
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        return getConnection("utopia");
    }

    /**
     * Get a database connection.
     * @throws ClassNotFoundException The MySQL JDBC driver is likely not installed.
     * @throws SQLException Failed to connect to the MySQL database.
     * @return Configured database connection.
     */
    public static Connection getConnection(String schema) throws ClassNotFoundException, SQLException {
        synchronized (DatabaseConnection.class) {
            if (!driverLoaded) {
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                driverLoaded = true;
            }
        }
        Connection connection = DriverManager.getConnection(String.format(DATABASE_URL, schema));
        connection.setAutoCommit(false);
        connection.setSchema(schema);
        return connection;
    }
}
