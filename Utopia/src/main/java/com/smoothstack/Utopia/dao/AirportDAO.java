package com.smoothstack.Utopia.dao;

import com.smoothstack.Utopia.data.flights.Airport;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AirportDAO extends BaseDAO<Airport> {
    PreparedStatement insert, delete, get_id, update, all;
    /**
     * Initializes the internal connection ready for use
     *
     * @throws SQLException           The connection could not be made.
     * @throws ClassNotFoundException The database driver could not be loaded.
     */
    public AirportDAO() throws SQLException, ClassNotFoundException {
        super();
    }

    /**
     * @param rs The result set
     * @return A TestDT
     * @throws SQLException Could not get from resultset
     */
    @Override
    protected Airport convertTo(ResultSet rs) throws SQLException {
        return new Airport(rs.getString("iata_id"), rs.getString("city"));
    }

    /**
     * Insert the object into the database. ID is ignored and auto-assigned to new value.
     * @param target The object to add. Should not be modified, else risk of heap pollution occurs.
     * @return A PreparedStatement to insert a single object
     * @throws SQLException Could not insert
     */
    @Override
    protected PreparedStatement convertInsert(Airport target) throws SQLException {
        if (insert == null) {
            insert = this.connection.prepareStatement("INSERT INTO airport (iata_id, city) VALUES (?, ?)");
        }
        insert.setString(1, target.get_iata());
        insert.setString(2, target.getCity());
        return insert;
    }

    /**
     * @param target The object to remove. Should not be modified, else risk of heap pollution occurs.
     * @return A PreparedStatement to delete a single object
     * @throws SQLException
     */
    @Override
    protected PreparedStatement convertDelete(Airport target) throws SQLException {
        if (delete == null) {
            delete = this.connection.prepareStatement("DELETE FROM airport WHERE iata_id = ?");
        }
        delete.setString(1, target.get_iata());
        return delete;
    }

    /**
     * @param targetOld The old object with old values
     * @param targetNew The new object with new values
     * @return
     * @throws SQLException
     */
    @Override
    protected PreparedStatement convertUpdate(Airport targetOld, Airport targetNew) throws SQLException {
        if (update == null) {
            update = this.connection.prepareStatement("UPDATE airport SET iata_id=?, city=? WHERE iata_id=?");
        }
        update.setString(1, targetNew.get_iata());
        update.setString(2, targetNew.getCity());
        update.setString(2, targetOld.get_iata());
        return update;
    }

    public Airport getFromIATA(String iata) throws SQLException {
        if (get_id == null) {
            get_id = connection.prepareStatement("SELECT * FROM airport WHERE iata_id=?");
        }
        get_id.setString(1, iata);
        ResultSet rs = get_id.executeQuery();
        if(rs.next()) {
            return new Airport(rs.getString("iata_id"), rs.getString("city"));
        }
        return null;
    }

    public ArrayList<Airport> getAll() throws SQLException {
        if (all == null) {
            all = connection.prepareStatement("SELECT * FROM airport");
        }
        return select(all);
    }

    public void insertDirect(String iata, String city) throws SQLException {
        if (insert == null) {
            insert = this.connection.prepareStatement("INSERT INTO airport (iata_id, city) VALUES (?, ?)");
        }
        insert.setString(1, iata);
        insert.setString(2, city);
        insert.executeUpdate();
    }

    public boolean exists(String iata) throws SQLException {
        return getFromIATA(iata) != null;
    }
}
