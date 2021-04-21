package com.smoothstack.Utopia.dao.users;

import com.smoothstack.Utopia.dao.BaseDAO;
import com.smoothstack.Utopia.data.users.UserRole;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;

public class UserRoleDAO extends BaseDAO<UserRole> {
    /**
     * @param connection A database connection. Should be valid.
     */
    public UserRoleDAO(Connection connection) {
        super(connection);
    }

    public UserRole insert(String name) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement(
                "INSERT INTO user_role (name)" +
                        "VALUES (?);",
                Statement.RETURN_GENERATED_KEYS
        );

        ps.setString(1, name);
        ps.executeUpdate();

        ResultSet keys = ps.getGeneratedKeys();
        keys.first();
        return new UserRole(keys.getInt(1), name);
    }

    public UserRole getByID(int id) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement("SELECT * FROM user_role WHERE id=?");
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
        PreparedStatement ps = db_connection.prepareStatement("DELETE FROM user_role WHERE id=?");
        ps.setInt(1, assigned.getId());
        ps.executeUpdate();
    }

    @Override
    protected UserRole convert(ResultSet rs) throws SQLException {
        throw new NotImplementedException();
    }
}
