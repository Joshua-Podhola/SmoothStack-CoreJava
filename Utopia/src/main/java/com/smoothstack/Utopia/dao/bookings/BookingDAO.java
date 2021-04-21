package com.smoothstack.Utopia.dao.bookings;

import com.smoothstack.Utopia.dao.BaseDAO;
import com.smoothstack.Utopia.data.bookings.Booking;
import com.smoothstack.Utopia.data.flights.Flight;

import java.sql.*;
import java.util.ArrayList;

public class BookingDAO extends BaseDAO<Booking> {

    /**
     * @param connection A database connection. Should be valid.
     */
    public BookingDAO(Connection connection) {
        super(connection);
    }

    public Booking insert(boolean is_active, String confirmation_code) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement(
                "INSERT INTO booking (is_active, confirmation_code) " +
                        "VALUES (?, ?);",
                Statement.RETURN_GENERATED_KEYS
        );

        ps.setBoolean(1, is_active);
        ps.setString(2, confirmation_code);
        ps.executeUpdate();

        ResultSet keys = ps.getGeneratedKeys();
        keys.first();
        return new Booking(keys.getInt(1), is_active, confirmation_code);
    }

    public void addFlight(Flight flight) throws SQLException {
        assertHasAssignment();
        PreparedStatement ps = db_connection.prepareStatement(
                "INSERT INTO flight_bookings (flight_id, booking_id) " +
                        "VALUES (?, ?);"
        );

        ps.setInt(1, flight.getId());
        ps.setInt(2, assigned.getId());
        ps.executeUpdate();
    }

    public void removeFlight(Flight flight) throws SQLException {
        assertHasAssignment();
        PreparedStatement ps = db_connection.prepareStatement(
                "DELETE FROM flight_bookings WHERE flight_id =? AND booking_id=?;"
        );

        ps.setInt(1, flight.getId());
        ps.setInt(2, assigned.getId());
        ps.executeUpdate();
    }

    public Booking getByID(int id) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement("SELECT * FROM booking WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return convert(rs);
        }
        return null;
    }

    public ArrayList<Booking> searchByUsername(String username) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement("SELECT b.id, b.confirmation_code, b.is_active " +
                "FROM booking b, booking_user bu, user u " +
                "WHERE b.id = bu.booking_id " +
                "AND bu.user_id = u.id " +
                "AND u.username LIKE ?");
        ps.setString(1, String.format("%%%s%%", username));
        ResultSet rs = ps.executeQuery();
        ArrayList<Booking> bookings = new ArrayList<>();
        while(rs.next()) {
            bookings.add(convert(rs));
        }
        //Shamelessly just copy and edit the above block of code
        ps = db_connection.prepareStatement("SELECT b.id, b.confirmation_code, b.is_active " +
                "FROM booking b, booking_agent bu, user u " +
                "WHERE b.id = bu.booking_id " +
                "AND bu.agent_id = u.id " +
                "AND u.username LIKE ?");
        ps.setString(1, String.format("%%%s%%", username));
        rs = ps.executeQuery();
        while(rs.next()) {
            bookings.add(convert(rs));
        }
        return bookings;
    }

    public ArrayList<Booking> searchByConfirmationCode(String confirmation_code) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement("SELECT b.id, b.confirmation_code, b.is_active " +
                "FROM booking b " +
                "WHERE b.confirmation_code LIKE ?");
        ps.setString(1, String.format("%%%s%%", confirmation_code));
        ResultSet rs = ps.executeQuery();
        ArrayList<Booking> bookings = new ArrayList<>();
        while(rs.next()) {
            bookings.add(convert(rs));
        }

        return bookings;
    }

    /**
     * Lazy initialize passengers
     *
     * @throws SQLException SQL Error
     */
    public void lazyPassengers() throws SQLException {
        assertHasAssignment();
        if (assigned.getPassengers() != null) {
            return;
        }
        PassengerDAO passengerDAO = new PassengerDAO(db_connection);
        assigned.setPassengers(passengerDAO.search(assigned));
    }

    public void updateActive(boolean is_active) throws SQLException {
        assertHasAssignment();
        PreparedStatement ps = db_connection.prepareStatement("UPDATE booking SET is_active=? WHERE id=?");
        ps.setBoolean(1, is_active);
        ps.setInt(2, assigned.getId());
        ps.executeUpdate();
    }

    @Override
    public void delete() throws SQLException {
        assertHasAssignment();
        PreparedStatement ps = db_connection.prepareStatement("DELETE FROM booking WHERE id=?");
        ps.setInt(1, assigned.getId());
        ps.executeUpdate();
    }

    @Override
    protected Booking convert(ResultSet rs) throws SQLException {
        return new Booking(rs.getInt("id"), rs.getBoolean("is_active"),
                rs.getString("confirmation_code"));
    }
}
