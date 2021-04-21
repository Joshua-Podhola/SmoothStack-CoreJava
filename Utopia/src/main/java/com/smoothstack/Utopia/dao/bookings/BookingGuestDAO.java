package com.smoothstack.Utopia.dao.bookings;

import com.smoothstack.Utopia.dao.BaseDAO;
import com.smoothstack.Utopia.data.bookings.Booking;
import com.smoothstack.Utopia.data.bookings.BookingGuest;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;

public class BookingGuestDAO extends BaseDAO<BookingGuest> {
    public BookingGuestDAO(Connection connection) {
        super(connection);
    }

    public BookingGuest insert(Booking booking, String email, String phone) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement(
                "INSERT INTO booking_guest (booking_id, contact_email, contact_phone) " +
                        "VALUES (?, ?, ?);",
                Statement.RETURN_GENERATED_KEYS
        );

        ps.setInt(1, booking.getId());
        ps.setString(2, email);
        ps.setString(3, phone);
        ps.executeUpdate();

        ResultSet keys = ps.getGeneratedKeys();
        keys.first();
        return new BookingGuest(booking, email, phone);
    }

    public BookingGuest getByID(int id) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement("SELECT * FROM booking_guest WHERE booking_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return convert(rs);
        }
        return null;
    }

    @Override
    public void delete() throws SQLException {
        assertHasAssignment();
        PreparedStatement ps = db_connection.prepareStatement("DELETE FROM booking_guest WHERE booking_id=?");
        ps.setInt(1, assigned.getBooking().getId());
        ps.executeUpdate();
    }

    @Override
    protected BookingGuest convert(ResultSet rs) throws SQLException {
        throw new NotImplementedException();
    }
}
