package com.smoothstack.Utopia.dao.flights;

import com.smoothstack.Utopia.dao.BaseDAO;
import com.smoothstack.Utopia.data.flights.Airport;
import com.smoothstack.Utopia.data.flights.Flight;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.ArrayList;

public class AirportDAO extends BaseDAO<Airport> {
    /**
     * @param connection A database connection. Should be valid.
     */
    public AirportDAO(Connection connection) {
        super(connection);
    }

    public Airport insert(String iata, String city) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement(
                "INSERT INTO airport (iata_id, city) " +
                        "VALUES (?, ?);",
                Statement.RETURN_GENERATED_KEYS
        );

        ps.setString(1, iata);
        ps.setString(2, city);
        ps.executeUpdate();

        ResultSet keys = ps.getGeneratedKeys();
        keys.first();
        return new Airport(iata, city);
    }

    public Airport getByID(String iata) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement("SELECT * FROM airport WHERE iata_id=?");
        ps.setString(1, iata);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return convert(rs);
        }
        return null;
    }

    public ArrayList<Airport> search(String city) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement("SELECT *" +
                "FROM airport " +
                "WHERE city LIKE ?");
        ///Yes, as silly as it looks, that is how you're meant to do it.
        ps.setString(1, String.format("%%%s%%", city));
        ResultSet rs = ps.executeQuery();
        ArrayList<Airport> airports = new ArrayList<>();
        while(rs.next()) {
            airports.add(convert(rs));
        }
        return airports;
    }

    @Override
    public void delete() throws SQLException {
        assertHasAssignment();
        PreparedStatement ps = db_connection.prepareStatement("DELETE FROM airport WHERE iata_id=?");
        ps.setString(1, assigned.get_iata());
        ps.executeUpdate();
    }

    @Override
    protected Airport convert(ResultSet rs) throws SQLException {
        return new Airport(
                rs.getString("iata_id"),
                rs.getString("city")
        );
    }
}
