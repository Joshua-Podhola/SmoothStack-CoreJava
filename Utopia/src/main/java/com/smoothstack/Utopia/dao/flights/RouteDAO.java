package com.smoothstack.Utopia.dao.flights;

import com.smoothstack.Utopia.dao.BaseDAO;
import com.smoothstack.Utopia.data.flights.Airport;
import com.smoothstack.Utopia.data.flights.Route;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.ArrayList;

public class RouteDAO extends BaseDAO<Route> {
    /**
     * @param connection A database connection. Should be valid.
     */
    public RouteDAO(Connection connection) {
        super(connection);
    }

    public Route insert(Airport origin, Airport destination) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement(
                "INSERT INTO route (origin_id, destination_id) " +
                        "VALUES (?, ?);",
                Statement.RETURN_GENERATED_KEYS
        );

        ps.setString(1, origin.get_iata());
        ps.setString(2, destination.get_iata());
        ps.executeUpdate();

        ResultSet keys = ps.getGeneratedKeys();
        keys.first();
        return new Route(keys.getInt(1), origin, destination);
    }

    public Route getByID(int id) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement("SELECT * FROM route WHERE id=?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return convert(rs);
        }
        return null;
    }

    public ArrayList<Route> searchByOriginIATA(String origin) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement("SELECT *" +
                "FROM route " +
                "WHERE origin_id=?");
        ///Yes, as silly as it looks, that is how you're meant to do it.
        ps.setString(1, origin);
        ResultSet rs = ps.executeQuery();
        ArrayList<Route> routes = new ArrayList<>();
        while(rs.next()) {
            routes.add(convert(rs));
        }
        return routes;
    }

    public ArrayList<Route> searchByDestinationIATA(String destination) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement("SELECT *" +
                "FROM route " +
                "WHERE destination_id=?");
        ///Yes, as silly as it looks, that is how you're meant to do it.
        ps.setString(1, destination);
        ResultSet rs = ps.executeQuery();
        ArrayList<Route> routes = new ArrayList<>();
        while(rs.next()) {
            routes.add(convert(rs));
        }
        return routes;
    }

    public ArrayList<Route> searchByIATA(String iata) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement("SELECT *" +
                "FROM route " +
                "WHERE destination_id=? " +
                "OR origin_id = ?");
        ///Yes, as silly as it looks, that is how you're meant to do it.
        ps.setString(1, iata);
        ps.setString(2, iata);
        ResultSet rs = ps.executeQuery();
        ArrayList<Route> routes = new ArrayList<>();
        while(rs.next()) {
            routes.add(convert(rs));
        }
        return routes;
    }

    public Route searchByIATAPair(String origin, String destination) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement("SELECT *" +
                "FROM route " +
                "WHERE destination_id=? " +
                "AND origin_id = ?");
        ///Yes, as silly as it looks, that is how you're meant to do it.
        ps.setString(1, origin);
        ps.setString(2, destination);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            return convert(rs);
        }
        return null;
    }

    @Override
    public void delete() throws SQLException {
        assertHasAssignment();
        PreparedStatement ps = db_connection.prepareStatement("DELETE FROM route WHERE id=?");
        ps.setInt(1, assigned.getId());
        ps.executeUpdate();
    }

    @Override
    protected Route convert(ResultSet rs) throws SQLException {
        AirportDAO airportDAO = new AirportDAO(db_connection);
        return new Route(
                rs.getInt("id"),
                airportDAO.getByID(rs.getString("origin_id")),
                airportDAO.getByID(rs.getString("destination_id"))
        );
    }
}
