package com.smoothstack.Utopia.dao.flights;

import com.smoothstack.Utopia.dao.BaseDAO;
import com.smoothstack.Utopia.data.flights.Airplane;
import com.smoothstack.Utopia.data.flights.AirplaneType;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;

public class AirplaneDAO extends BaseDAO<Airplane> {
    /**
     * @param connection A database connection. Should be valid.
     */
    public AirplaneDAO(Connection connection) {
        super(connection);
    }

    public Airplane insert(AirplaneType airplaneType) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement(
                "INSERT INTO airplane (type_id) " +
                        "VALUES (?);",
                Statement.RETURN_GENERATED_KEYS
        );

        ps.setInt(1, airplaneType.getId());
        ps.executeUpdate();

        ResultSet keys = ps.getGeneratedKeys();
        keys.first();
        return new Airplane(keys.getInt(1), airplaneType);
    }

    public Airplane getByID(int id) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement("SELECT * FROM airplane WHERE id=?");
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
        PreparedStatement ps = db_connection.prepareStatement("DELETE FROM airplane WHERE id=?");
        ps.setInt(1, assigned.getId());
        ps.executeUpdate();
    }

    @Override
    protected Airplane convert(ResultSet rs) throws SQLException {
        AirplaneTypeDAO airplaneTypeDAO = new AirplaneTypeDAO(db_connection);
        return new Airplane(
                rs.getInt("id"),
                airplaneTypeDAO.getByID(rs.getInt("type_id"))
        );
    }
}
