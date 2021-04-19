package com.smoothstack.Utopia.dao;

import com.smoothstack.Utopia.data.flights.Airplane;
import com.smoothstack.Utopia.data.flights.AirplaneType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AirplanesDAO extends BaseDAO<Airplane> {
    /**
     * Initializes the internal connection ready for use
     *
     * @throws SQLException           The connection could not be made.
     * @throws ClassNotFoundException The database driver could not be loaded.
     */
    public AirplanesDAO() throws SQLException, ClassNotFoundException {
        super();
    }

    PreparedStatement insert, delete, update, airplaneid, airplanetypeid, airplanetypecapacity, all, insertType;

    /**
     * @param rs The result set
     * @return A TestDT
     * @throws SQLException Could not get from resultset
     */
    @Override
    protected Airplane convertTo(ResultSet rs) throws SQLException {
        return new Airplane(
                rs.getInt("id"),
                typeFromID(rs.getInt("type_id"))
        );
    }

    /**
     * Insert the object into the database. ID is ignored and auto-assigned to new value.
     * @param target The object to add. Should not be modified, else risk of heap pollution occurs.
     * @return A PreparedStatement to insert a single object
     * @throws SQLException Could not insert
     */
    @Override
    protected PreparedStatement convertInsert(Airplane target) throws SQLException {
        if (insert == null) {
            insert = this.connection.prepareStatement("INSERT INTO airplane (type_id) VALUES (?)");
        }
        insert.setInt(1, target.getType().getId());
        return insert;
    }

    /**
     * @param target The object to remove. Should not be modified, else risk of heap pollution occurs.
     * @return PreparedStatement
     * @throws SQLException SQL Error
     */
    @Override
    protected PreparedStatement convertDelete(Airplane target) throws SQLException {
        if (delete == null) {
            delete = this.connection.prepareStatement("DELETE FROM airplane WHERE id = ?");
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
    protected PreparedStatement convertUpdate(Airplane targetOld, Airplane targetNew) throws SQLException {
        assert(targetOld.getId().equals(targetNew.getId()));
        if (update == null) {
            update = this.connection.prepareStatement("UPDATE airplane SET type_id=? WHERE id=?");
        }
        update.setInt(1, targetNew.getType().getId());
        update.setInt(2, targetOld.getId());
        return update;
    }

    /**
     * @param id ID of the airplane
     * @return An Airplane if found, else null
     * @throws SQLException SQL Error
     */
    public Airplane airplaneFromID(int id) throws SQLException {
        if (airplaneid == null) {
            airplaneid = connection.prepareStatement("SELECT * FROM airplane WHERE id=?");
        }
        airplaneid.setInt(1, id);
        ResultSet rs_plane = airplaneid.executeQuery();
        if(rs_plane.next()) {
            int type_id = rs_plane.getInt("type_id"), plane_id = rs_plane.getInt("id");
            AirplaneType type = typeFromID(type_id);
            if(type == null) {
                return null;
            }
            return new Airplane(plane_id, type);
        }
        return null;
    }

    /**
     * Get an AirplaneType from its ID
     * @param id The ID
     * @return The airplanetype, or null if not found
     * @throws SQLException SQL Error
     */
    public AirplaneType typeFromID(int id) throws SQLException {
        if (airplanetypeid == null) {
            airplanetypeid = connection.prepareStatement("SELECT id, max_capacity FROM airplane_type WHERE ID = ?");
        }
        airplanetypeid.setInt(1, id);
        ResultSet rs = airplanetypeid.executeQuery();
        if(rs.next()) {
            return new AirplaneType(rs.getInt("id"), rs.getInt("max_capacity"));
        }
        return null;
    }

    /**
     * Get an AirplaneType from its capacity. Grabs the first result.
     * @param capacity The capacity
     * @return The airplanetype, or null if not found
     * @throws SQLException SQL Error
     */
    public AirplaneType typeFromCapacity(int capacity) throws SQLException {
        if (airplanetypecapacity == null) {
            airplanetypecapacity = connection.prepareStatement("SELECT id, max_capacity FROM airplane_type WHERE max_capacity = ?");
        }
        airplanetypecapacity.setInt(1, capacity);
        ResultSet rs = airplanetypecapacity.executeQuery();
        if(rs.next()) {
            return new AirplaneType(rs.getInt("id"), rs.getInt("max_capacity"));
        }
        return null;
    }

    /**
     * Enters an airplane directly. Tries to find a type with the given capacity automatically.
     * @param capacity The airplane capacity.
     * @throws SQLException SQL Error
     */
    public void insertDirect(int capacity) throws SQLException {
        if (insert == null) {
            insert = this.connection.prepareStatement("INSERT INTO airplane (type_id) VALUES (?)");
        }
        AirplaneType type = typeFromCapacity(capacity);
        if(type == null) {
            int id = insertType(capacity);
            type = typeFromID(id);
        }

        insert.setInt(1, type.getId());
        insert.executeUpdate();
    }

    /**
     * Insert a new type into the database. Returns the ID of the new type.
     * @param capacity The capacity of the new type
     * @return The ID of the new type.
     * @throws SQLException SQL Error
     */
    public int insertType(int capacity) throws SQLException {
        if (insertType == null) {
            insertType = connection.prepareStatement("INSERT INTO airplane_type (max_capacity) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
        }
        insertType.setInt(1, capacity);
        insertType.executeUpdate();
        ResultSet keys = insertType.getGeneratedKeys();
        if(keys.next()) {
            return keys.getInt(1);
        }
        return -1;
    }

    /**
     * @return ArrayList of all Airplanes
     * @throws SQLException SQL Error
     */
    public ArrayList<Airplane> getAll() throws SQLException {
        if (all == null) {
            all = connection.prepareStatement("SELECT * FROM airplane;");
        }
        return select(all);
    }
}
