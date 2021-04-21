package com.smoothstack.Utopia.dao.flights;

import com.smoothstack.Utopia.dao.BaseDAO;
import com.smoothstack.Utopia.data.flights.Flight;
import com.smoothstack.Utopia.data.flights.FlightSeats;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;

public class FlightSeatsDAO extends BaseDAO<FlightSeats> {
    /**
     * @param connection A database connection. Should be valid.
     */
    public FlightSeatsDAO(Connection connection) {
        super(connection);
    }

    public FlightSeats insert(Flight flight, int economy, int first, int business) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement(
                "INSERT INTO flight_seats (flight_id, economy, first, business) " +
                        "VALUES (?, ?, ?, ?);",
                Statement.RETURN_GENERATED_KEYS
        );

        ps.setInt(1, flight.getId());
        ps.setInt(2, economy);
        ps.setInt(2, first);
        ps.setInt(2, business);
        ps.executeUpdate();

        ResultSet keys = ps.getGeneratedKeys();
        keys.first();
        return new FlightSeats(flight, first, business, economy);
    }

    public FlightSeats getByID(int flight_id) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement("SELECT * FROM flight_seats WHERE flight_id=?");
        ps.setInt(1, flight_id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return convert(rs);
        }
        return null;
    }

    @Override
    public void delete() throws SQLException {
        assertHasAssignment();
        PreparedStatement ps = db_connection.prepareStatement("DELETE FROM flight_seats WHERE flight_id=?");
        ps.setInt(1, assigned.getFlight().getId());
        ps.executeUpdate();
    }

    @Override
    protected FlightSeats convert(ResultSet rs) throws SQLException {
        throw new NotImplementedException();
    }
}
