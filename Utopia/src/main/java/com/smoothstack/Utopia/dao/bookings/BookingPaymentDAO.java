package com.smoothstack.Utopia.dao.bookings;

import com.smoothstack.Utopia.dao.BaseDAO;
import com.smoothstack.Utopia.data.bookings.Booking;
import com.smoothstack.Utopia.data.bookings.BookingPayment;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;

public class BookingPaymentDAO extends BaseDAO<BookingPayment> {
    /**
     * @param connection A database connection. Should be valid.
     */
    public BookingPaymentDAO(Connection connection) {
        super(connection);
    }

    public BookingPayment insert(Booking booking, String stripe, boolean is_refunded) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement(
                "INSERT INTO booking_payment (booking_id, stripe_id, refunded) " +
                        "VALUES (?, ?, ?);",
                Statement.RETURN_GENERATED_KEYS
        );

        ps.setInt(1, booking.getId());
        ps.setString(2, stripe);
        ps.setBoolean(3, is_refunded);
        ps.executeUpdate();

        ResultSet keys = ps.getGeneratedKeys();
        keys.first();
        return new BookingPayment(booking, stripe, is_refunded);
    }

    public BookingPayment getByID(int id) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement("SELECT * FROM booking_payment WHERE booking_id = ?");
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
        PreparedStatement ps = db_connection.prepareStatement("DELETE FROM booking_payment WHERE booking_id=?");
        ps.setInt(1, assigned.getBooking().getId());
        ps.executeUpdate();
    }

    @Override
    protected BookingPayment convert(ResultSet rs) throws SQLException {
        throw new NotImplementedException();
    }
}
