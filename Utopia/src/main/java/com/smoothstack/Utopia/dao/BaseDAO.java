package com.smoothstack.Utopia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Base class for DAOs.
 *
 * @param <TargetType> The entity type to operate on.
 */
public abstract class BaseDAO<TargetType> {
    //A reference to the entity we are working on.
    protected TargetType assigned;
    protected Connection db_connection;

    /**
     * @param connection A database connection. Should be valid.
     */
    public BaseDAO(Connection connection) {
        this.db_connection = connection;
    }

    public Connection getConnection() {
        return db_connection;
    }

    public void setConnection(Connection connection) {
        this.db_connection = connection;
    }


    /**
     * Throws an exception of Assigned is null. Should be called any time an update is performed on the object.
     */
    protected void assertHasAssignment() {
        if (assigned == null) {
            throw new IllegalStateException("No assigned object.");
        }
    }

    public void setAssigned(TargetType target) {
        assigned = target;
    }

    public TargetType getAssigned() {
        return assigned;
    }

    //Derivative classes should provide update methods for each member of the entity class.
    //It is expected that when updating members of entity type, its DAO is used to update it.
    //In this case, it is outside the scope of this DAO and the task is delegated to it instead.
    //Note that it is considered illegal to change the primary key of a table, any finals of primitive
    //type should never have an update function for it.

    //Derivative classes are also free to create their own methods for queries.

    //Should also provide searchBy() overloads for different parameters returning a Collection<TargetType>

    /**
     * Delete the assigned object
     *
     * @throws SQLException SQL Error
     */
    public abstract void delete() throws SQLException;

    /**
     * Convert a Result to type
     */
    protected abstract TargetType convert(ResultSet rs) throws SQLException;
}
