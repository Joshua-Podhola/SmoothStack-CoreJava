package com.smoothstack.Utopia.dao.bookings;

import com.smoothstack.Utopia.dao.BaseDAO;
import com.smoothstack.Utopia.data.bookings.Booking;
import com.smoothstack.Utopia.data.bookings.Passenger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class PassengerDAO extends BaseDAO<Passenger> {
    /**
     * @param connection A database connection. Should be valid.
     */
    public PassengerDAO(Connection connection) {
        super(connection);
    }

    /**
     * Insert a new passenger into the database, then return it. Initializes known lazy values.
     *
     * @param booking     The booking it belongs to.
     * @param given_name  First name
     * @param family_name Family Name
     * @param dob         Date of Birth
     * @param gender      Gender
     * @param address     Address
     * @return Passenger
     * @throws SQLException SQL Error
     */
    public Passenger insert(Booking booking, String given_name, String family_name, LocalDate dob, String gender, String address) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement(
                "INSERT INTO passenger (booking_id, given_name, family_name, dob, gender, address) " +
                        "VALUES (?, ?, ?, ?, ?, ?);",
                Statement.RETURN_GENERATED_KEYS
        );

        ps.setInt(1, booking.getId());
        ps.setString(2, given_name);
        ps.setString(3, family_name);
        ps.setDate(4, Date.valueOf(dob));
        ps.setString(5, gender);
        ps.setString(6, address);
        ps.executeUpdate();

        ResultSet keys = ps.getGeneratedKeys();
        keys.first();
        Passenger p = new Passenger(keys.getInt(1), booking, given_name, family_name, gender, address, dob);
        p.setBooking(booking);
        return p;
    }

    /**
     * Get a passenger by their ID
     *
     * @param id The passenger's ID
     * @return Passenger, or null if not found.
     */
    public Passenger getByID(int id) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement("SELECT * FROM passenger WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return convert(rs);
        }
        return null;
    }

    /**
     * Search by booking
     *
     * @param booking Booking
     * @return ArrayList of Passenger
     */
    public ArrayList<Passenger> search(Booking booking) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement("SELECT * FROM passenger WHERE booking_id = ?");
        ps.setInt(1, booking.getId());
        ResultSet rs = ps.executeQuery();
        ArrayList<Passenger> results = new ArrayList<>();

        while (rs.next()) {
            Passenger p = convert(rs);
            p.setBooking(booking);
            results.add(p);
        }

        return results;
    }

    /**
     * Lazy initialize Booking
     *
     * @throws SQLException SQL Error
     */
    public void lazyBooking() throws SQLException {
        assertHasAssignment();
        if (assigned.getBooking() != null) {
            return;
        }
        BookingDAO bookingDAO = new BookingDAO(db_connection);
        PreparedStatement ps = db_connection.prepareStatement("SELECT booking_id FROM passenger WHERE id = ?");
        ps.setInt(1, assigned.getId());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int id = rs.getInt(1);
            assigned.setBooking(bookingDAO.getByID(id));
        }
    }

    public void updateName(String given_name, String family_name) throws SQLException {
        assertHasAssignment();
        PreparedStatement ps = db_connection.prepareStatement("UPDATE passenger SET given_name=?, family_name=? WHERE id = ?");
        ps.setString(1, given_name);
        ps.setString(2, family_name);
        ps.setInt(3, assigned.getId());
        ps.executeUpdate();

    }

    @Override
    public void delete() throws SQLException {
        assertHasAssignment();
        PreparedStatement ps = db_connection.prepareStatement("DELETE FROM passenger WHERE id=?");
        ps.setInt(1, assigned.getId());
        ps.executeUpdate();
    }

    @Override
    protected Passenger convert(ResultSet rs) throws SQLException {
        BookingDAO bookingDAO = new BookingDAO(db_connection);
        return new Passenger(rs.getInt("id"),
                bookingDAO.getByID(rs.getInt("booking_id")),
                rs.getString("given_name"),
                rs.getString("family_name"),
                rs.getString("gender"),
                rs.getString("address"),
                rs.getDate("dob").toLocalDate());
    }
}
