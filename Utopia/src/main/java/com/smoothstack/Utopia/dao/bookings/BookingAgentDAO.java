package com.smoothstack.Utopia.dao.bookings;

import com.smoothstack.Utopia.dao.BaseDAO;
import com.smoothstack.Utopia.data.bookings.Booking;
import com.smoothstack.Utopia.data.bookings.BookingAgent;
import com.smoothstack.Utopia.data.users.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;

public class BookingAgentDAO extends BaseDAO<BookingAgent> {

    public BookingAgentDAO(Connection connection) {
        super(connection);
    }

    public BookingAgent insert(Booking booking, User agent) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement(
                "INSERT INTO booking_agent (booking_id, agent_id) " +
                        "VALUES (?, ?);",
                Statement.RETURN_GENERATED_KEYS
        );

        ps.setInt(1, booking.getId());
        ps.setInt(2, agent.getId());
        ps.executeUpdate();

        ResultSet keys = ps.getGeneratedKeys();
        keys.first();
        return new BookingAgent(booking, agent);
    }

    public BookingAgent getByID(int booking_id, int agent_id) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement("SELECT * FROM booking_agent WHERE booking_id = ? AND agent_id=?");
        ps.setInt(1, booking_id);
        ps.setInt(2, agent_id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return convert(rs);
        }
        return null;
    }

    @Override
    public void delete() throws SQLException {
        assertHasAssignment();
        PreparedStatement ps = db_connection.prepareStatement("DELETE FROM booking_agent WHERE booking_id=? AND agent_id=?");
        ps.setInt(1, assigned.getBooking().getId());
        ps.setInt(2, assigned.getAgent().getId());
        ps.executeUpdate();
    }

    @Override
    protected BookingAgent convert(ResultSet rs) throws SQLException {
        throw new NotImplementedException();
    }
}
