package com.smoothstack.Utopia.dao;

import com.smoothstack.Utopia.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@SuppressWarnings("FieldCanBeLocal")
public abstract class BaseDAO<T> implements AutoCloseable {
    protected final Connection connection;

    /**
     * Initializes the internal connection ready for use
     * @throws SQLException The connection could not be made.
     * @throws ClassNotFoundException The database driver could not be loaded.
     */
    public BaseDAO(String schema) throws SQLException, ClassNotFoundException {
        this.connection = DatabaseConnection.getConnection(schema);
        this.connection.setAutoCommit(false);
    }

    /**
     * Use a precompiled statement to get a list of elements of the given class type.
     * @param statement SQL Statement
     * @return An ArrayList of the object
     * @throws SQLException Could not retrieve objects
     */
    public ArrayList<T> select(PreparedStatement statement) throws SQLException {
        ArrayList<T> elements = new ArrayList<>();
        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            elements.add(this.convertTo(rs));
        }

        return elements;
    }

    /**
     * Insert new entries into the database
     * @param targets The targets to add
     * @throws SQLException Failed to add to database
     */
    @SafeVarargs
    public final void create(final T...targets) throws SQLException {
        for (T x : targets) {
            convertInsert(x).execute();
        }
    }

    /**
     * Replaces a value in the DB with a new one, if it is found.
     * @param oldValue The old value to look for.
     * @param newValue The new value to use.
     * @throws SQLException The value could not be found/replaced.
     * @return Number of rows affected.
     */
    public int update(T oldValue, T newValue) throws SQLException {
        PreparedStatement ps = convertUpdate(oldValue, newValue);
        return ps.executeUpdate();
    }

    /**
     * Remove entries from the database
     * @param targets The targets to delete
     * @throws SQLException Failed to delete from database
     */
    @SafeVarargs
    public final void delete(final T...targets) throws SQLException {
        for (T x : targets) {
            convertDelete(x).execute();
        }
    }

    /**
     * Commits changes
     * @throws SQLException Could not commit changes.
     */
    public void commit() throws SQLException {
        connection.commit();
    }

    /**
     * Undo changes
     * @throws SQLException Could not rollback; changes not committed.
     */
    public void rollback() throws SQLException {
        connection.rollback();
    }

    public void close() throws SQLException {
        connection.close();
    }

    /**
     * Convert the current ResultSet pointed object to the DAO's object.
     * Should not move the pointer!
     * @param rs The result set
     * @return The object to be returned.
     */
    protected abstract T convertTo(ResultSet rs) throws SQLException;

    /**
     * Create a query to add the given objects to the database.
     * @param target The object to add. Should not be modified, else risk of heap pollution occurs.
     * @return A PreparedStatement populated with the needed values.
     */
    protected abstract PreparedStatement convertInsert(final T target) throws SQLException;

    /**
     * Create a query to remove the given object from the database
     * @param target The object to remove. Should not be modified, else risk of heap pollution occurs.
     * @return A PreparedStatement populated with the needed values.
     */
    protected abstract PreparedStatement convertDelete(final T target) throws SQLException;

    /**
     * Create a query to update the given object in the database.
     * @param targetOld The old object with old values
     * @param targetNew The new object with new values
     * @return A PreparedStatement populated with the needed values.
     */
    protected abstract PreparedStatement convertUpdate(final T targetOld, final T targetNew) throws SQLException;
}
