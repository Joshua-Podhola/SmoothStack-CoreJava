package com.smoothstack.Utopia.dao.flights;

import com.smoothstack.Utopia.dao.BaseDAO;
import com.smoothstack.Utopia.data.flights.AirplaneType;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;

public class AirplaneTypeDAO extends BaseDAO<AirplaneType> {
    /**
     * @param connection A database connection. Should be valid.
     */
    public AirplaneTypeDAO(Connection connection) {
        super(connection);
    }

    public AirplaneType insert(int capacity) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement(
                "INSERT INTO airplane_type (max_capacity) " +
                        "VALUES (?);",
                Statement.RETURN_GENERATED_KEYS
        );

        ps.setInt(1, capacity);
        ps.executeUpdate();

        ResultSet keys = ps.getGeneratedKeys();
        keys.first();
        return new AirplaneType(keys.getInt(1), capacity);
    }

    public AirplaneType getByID(int id) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement("SELECT * FROM airplane_type WHERE id=?");
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
        PreparedStatement ps = db_connection.prepareStatement("DELETE FROM airplane_type WHERE id=?");
        ps.setInt(1, assigned.getId());
        ps.executeUpdate();
    }

    @Override
    protected AirplaneType convert(ResultSet rs) throws SQLException {
        return new AirplaneType(
                rs.getInt("id"),
                rs.getInt("max_capacity")
        );
    }
}
