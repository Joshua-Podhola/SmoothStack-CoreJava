package com.smoothstack.Utopia.dao;

import com.smoothstack.Utopia.data.users.User;
import com.smoothstack.Utopia.data.users.UserRole;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDAO extends BaseDAO<User> {
    /**
     * Initializes the internal connection ready for use
     *
     * @throws SQLException           The connection could not be made.
     * @throws ClassNotFoundException The database driver could not be loaded.
     */
    public UserDAO() throws SQLException, ClassNotFoundException {
        super();
    }

    PreparedStatement insert, delete, update, userRoleID, userID, all, allRoles, insertType, byUsername;

    /**
     * @param rs The result set
     * @return A TestDT
     * @throws SQLException Could not get from resultset
     */
    @Override
    protected User convertTo(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                roleFromID(rs.getInt("role_id")),
                rs.getString("given_name"),
                rs.getString("family_name"),
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("phone")
        );
    }

    /**
     * Insert the object into the database. ID is ignored and auto-assigned to new value.
     * @param target The object to add. Should not be modified, else risk of heap pollution occurs.
     * @return A PreparedStatement to insert a single object
     * @throws SQLException Could not insert
     */
    @Override
    protected PreparedStatement convertInsert(User target) throws SQLException {
        if (insert == null) {
            insert = this.connection.prepareStatement("INSERT INTO user (role_id, given_name, family_name, username, email, password, phone) VALUES (?, ?, ?, ?, ?, ?, ?)");
        }
        insert.setInt(1, target.getRole().getId());
        insert.setString(2, target.getGiven_name());
        insert.setString(3, target.getFamily_name());
        insert.setString(4, target.getUsername());
        insert.setString(5, target.getEmail());
        insert.setString(6, target.getPassword());
        insert.setString(7, target.getPhone());
        return insert;
    }

    /**
     * @param target The object to remove. Should not be modified, else risk of heap pollution occurs.
     * @return PreparedStatement
     * @throws SQLException SQL Error
     */
    @Override
    protected PreparedStatement convertDelete(User target) throws SQLException {
        if (delete == null) {
            delete = this.connection.prepareStatement("DELETE FROM user WHERE id = ?");
        }
        delete.setInt(1, target.getId());
        return delete;
    }

    /**
     * @param targetOld The old object with old values
     * @param targetNew The new object with new values
     * @return PreparedStatement
     * @throws SQLException SQL Error
     * @throws AssertionError The IDs do not match
     */
    @Override
    protected PreparedStatement convertUpdate(User targetOld, User targetNew) throws SQLException {
        assert(targetOld.getId() == (targetNew.getId()));
        if (update == null) {
            update = this.connection.prepareStatement("UPDATE user SET role_id=?, given_name=?, family_name=?," +
                    "username=?, email=?, password=?, phone=? WHERE id=?");
        }
        update.setInt(1, targetNew.getRole().getId());
        update.setString(2, targetNew.getGiven_name());
        update.setString(3, targetNew.getFamily_name());
        update.setString(4, targetNew.getUsername());
        update.setString(5, targetNew.getEmail());
        update.setString(6, targetNew.getPassword());
        update.setString(7, targetNew.getPhone());
        update.setInt(8, targetOld.getId());
        return update;
    }

    /**
     * Get a UserRole from its ID
     * @param id The ID
     * @return The UserRole, or null if not found
     * @throws SQLException SQL Error
     */
    public UserRole roleFromID(int id) throws SQLException {
        if (userRoleID == null) {
            userRoleID = connection.prepareStatement("SELECT * FROM user_role WHERE ID = ?");
        }
        userRoleID.setInt(1, id);
        ResultSet rs = userRoleID.executeQuery();
        if(rs.next()) {
            return new UserRole(rs.getInt("id"), rs.getString("name"));
        }
        return null;
    }

    /**
     * Get a User from its ID
     * @param id The ID
     * @return The UserRole, or null if not found
     * @throws SQLException SQL Error
     */
    public User userFromID(int id) throws SQLException {
        if (userID == null) {
            userID = connection.prepareStatement("SELECT * FROM user WHERE ID = ?");
        }
        userRoleID.setInt(1, id);
        ResultSet rs = userRoleID.executeQuery();
        if(rs.next()) {
            return convertTo(rs);
        }
        return null;
    }

    /**
     * Get a User from its username
     * @param username Its username
     * @return The User, or null if not found
     * @throws SQLException SQL Error
     */
    public User userFromUsername(String username) throws SQLException {
        if (byUsername == null) {
            byUsername = connection.prepareStatement("SELECT * FROM user WHERE username = ?");
        }
        byUsername.setString(1, username);
        ResultSet rs = byUsername.executeQuery();
        if(rs.next()) {
            return convertTo(rs);
        }
        return null;
    }

    /**
     * @param roleID ID of role
     * @param given_name Given Name
     * @param family_name Family Name
     * @param username Username
     * @param email Email
     * @param password Password
     * @param phone Phone #
     * @throws SQLException SQL Error
     */
    public void insertDirect(int roleID, String given_name, String family_name, String username, String email,
                             String password, String phone) throws SQLException {
        if (insert == null) {
            insert = this.connection.prepareStatement("INSERT INTO user " +
                    "(role_id, given_name, family_name, username, email, password, phone) VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?)");
        }
        insert.setInt(1, roleID);
        insert.setString(2, given_name);
        insert.setString(3, family_name);
        insert.setString(4, username);
        insert.setString(5, email);
        insert.setString(6,password);
        insert.setString(7, phone);
        insert.executeUpdate();
    }

    /**
     * Insert a new role into the database. Returns the ID of the new role.
     * @param name The name
     * @return The ID of the new type.
     * @throws SQLException SQL Error
     */
    public int insertRole(String name) throws SQLException {
        if (insertType == null) {
            insertType = connection.prepareStatement("INSERT INTO user_role (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
        }
        insertType.setString(1, name);
        insertType.executeUpdate();
        ResultSet keys = insertType.getGeneratedKeys();
        if(keys.next()) {
            return keys.getInt(1);
        }
        return -1;
    }

    /**
     * @return ArrayList of all Users
     * @throws SQLException SQL Error
     */
    public ArrayList<User> getAll() throws SQLException {
        if (all == null) {
            all = connection.prepareStatement("SELECT * FROM user;");
        }
        return select(all);
    }

    /**
     * @return ArrayList of all UserRoles
     * @throws SQLException SQL Error
     */
    public ArrayList<UserRole> getAllRoles() throws SQLException {
        if (allRoles == null) {
            allRoles = connection.prepareStatement("SELECT * FROM user_role;");
        }
        ResultSet rs = allRoles.executeQuery();
        ArrayList<UserRole> roles = new ArrayList<>();
        while(rs.next()) {
            roles.add(new UserRole(rs.getInt("id"), rs.getString("name")));
        }

        return roles;
    }
}
