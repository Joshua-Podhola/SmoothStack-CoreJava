package com.smoothstack.Utopia.dao.bookings;

import com.smoothstack.Utopia.dao.BaseDAO;
import com.smoothstack.Utopia.data.bookings.Booking;
import com.smoothstack.Utopia.data.bookings.BookingUser;
import com.smoothstack.Utopia.data.users.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;

public class BookingUserDAO extends BaseDAO<BookingUser> {
    public BookingUserDAO(Connection connection) {
        super(connection);
    }

    public BookingUser insert(Booking booking, User user) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement(
                "INSERT INTO booking_user (booking_id, user_id) " +
                        "VALUES (?, ?);",
                Statement.RETURN_GENERATED_KEYS
        );

        ps.setInt(1, booking.getId());
        ps.setInt(2, user.getId());
        ps.executeUpdate();

        ResultSet keys = ps.getGeneratedKeys();
        keys.first();
        return new BookingUser(booking, user);
    }

    public BookingUser getByID(int booking_id, int user_id) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement("SELECT * FROM booking_user WHERE booking_id = ? AND user_id=?");
        ps.setInt(1, booking_id);
        ps.setInt(2, user_id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return convert(rs);
        }
        return null;
    }

    @Override
    public void delete() throws SQLException {
        assertHasAssignment();
        PreparedStatement ps = db_connection.prepareStatement("DELETE FROM booking_user WHERE booking_id=? AND user_id=?");
        ps.setInt(1, assigned.getBooking().getId());
        ps.setInt(2, assigned.getUser().getId());
        ps.executeUpdate();
    }

    @Override
    protected BookingUser convert(ResultSet rs) throws SQLException {
        throw new NotImplementedException();
    }
}
