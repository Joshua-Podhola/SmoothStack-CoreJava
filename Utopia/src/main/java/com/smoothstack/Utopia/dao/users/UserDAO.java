package com.smoothstack.Utopia.dao.users;

import com.smoothstack.Utopia.dao.BaseDAO;
import com.smoothstack.Utopia.data.users.User;
import com.smoothstack.Utopia.data.users.UserRole;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;

public class UserDAO extends BaseDAO<User> {
    /**
     * @param connection A database connection. Should be valid.
     */
    public UserDAO(Connection connection) {
        super(connection);
    }

    public User insert(UserRole role, String given_name, String family_name, String username, String email,
                       String password, String phone) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement(
                "INSERT INTO user (role_id, given_name, family_name, username, email, password, phone) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?);",
                Statement.RETURN_GENERATED_KEYS
        );

        ps.setInt(1, role.getId());
        ps.setString(2, given_name);
        ps.setString(3, family_name);
        ps.setString(4, username);
        ps.setString(5, email);
        ps.setString(6, password);
        ps.setString(7, phone);
        ps.executeUpdate();

        ResultSet keys = ps.getGeneratedKeys();
        keys.first();
        return new User(keys.getInt(1), role, given_name, family_name, username, email, password, phone);
    }

    public User getByID(int id) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement("SELECT * FROM user WHERE id=?");
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
        PreparedStatement ps = db_connection.prepareStatement("DELETE FROM user WHERE id=?");
        ps.setInt(1, assigned.getId());
        ps.executeUpdate();
    }

    @Override
    protected User convert(ResultSet rs) throws SQLException {
        throw new NotImplementedException();
    }
}
