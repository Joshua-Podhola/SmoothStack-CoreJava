package com.smoothstack.corejava.weekone;

import java.sql.*;


@SuppressWarnings("SqlNoDataSourceInspection")
public class SampleSingleton {

    private Connection conn = null;

    private static SampleSingleton instance = null;

    public static SampleSingleton getInstance() {
        if(instance == null) instance = new SampleSingleton();
        return instance;
    }

    /**
     * I'd love to tell you what this is supposed to do, but a lot of this just outright makes no sense.
     * No, I didn't design this, the problem was to try to fix this class.
     * @param input ...something?
     */
    public void databaseQuery(int input) {
        try {
            conn = DriverManager.getConnection("url of database");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select id from table");
            //It's ambiguous what this
            int x = 0;
            while (rs.next()) {
                x = rs.getInt(1) * input;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}

