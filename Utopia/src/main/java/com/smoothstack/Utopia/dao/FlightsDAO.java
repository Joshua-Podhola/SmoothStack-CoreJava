package com.smoothstack.Utopia.dao;

import com.smoothstack.Utopia.data.flights.Airport;
import com.smoothstack.Utopia.data.flights.Flight;
import com.smoothstack.Utopia.data.flights.FlightSeats;
import com.smoothstack.Utopia.data.flights.Route;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FlightsDAO extends BaseDAO<Flight> {
    /**
     * Initializes the internal connection ready for use
     *
     * @throws SQLException           The connection could not be made.
     * @throws ClassNotFoundException The database driver could not be loaded.
     */
    public FlightsDAO() throws SQLException, ClassNotFoundException {
        this("utopia");
    }

    /**
     * Initializes the internal connection ready for use
     *
     * @throws SQLException           The connection could not be made.
     * @throws ClassNotFoundException The database driver could not be loaded.
     */
    public FlightsDAO(String schema) throws SQLException, ClassNotFoundException {
        super(schema);
        airportDAO = new AirportDAO();
        airplaneDAO = new AirplanesDAO();
    }

    PreparedStatement insert, delete, update, routeid, airplaneid, airplanetypeid, all, routeiatapair, newroute, fromid,
            seats, insertSeats, updateFlightSeats;
    AirportDAO airportDAO;
    AirplanesDAO airplaneDAO;

    /**
     * @param rs The result set
     * @return A TestDT
     * @throws SQLException Could not get from resultset
     */
    @Override
    protected Flight convertTo(ResultSet rs) throws SQLException {
        return new Flight(
                rs.getInt("id"),
                routeFromID(rs.getInt("route_id")),
                airplaneDAO.airplaneFromID(rs.getInt("airplane_id")),
                rs.getTimestamp("departure_time").toLocalDateTime(),
                rs.getInt("reserved_seats"),
                rs.getFloat("seat_price")
                );
    }

    /**
     * Insert the object into the database. ID is ignored and auto-assigned to new value.
     * @param target The object to add. Should not be modified, else risk of heap pollution occurs.
     * @return A PreparedStatement to insert a single object
     * @throws SQLException Could not insert
     */
    @Override
    protected PreparedStatement convertInsert(Flight target) throws SQLException {
        if (insert == null) {
            insert = this.connection.prepareStatement("INSERT INTO flight (id, route_id, airplane_id, departure_time, reserved_seats, seat_price) VALUES (?, ?, ?, ?, ?, ?)");
        }
        insert.setInt(1, target.getId());
        insert.setInt(2, target.getRoute().getId());
        insert.setInt(3, target.getAirplane().getId());
        insert.setTimestamp(4, Timestamp.valueOf(target.getDeparture_time()));
        insert.setInt(5, target.getReserved_seats());
        insert.setFloat(6, target.getSeat_price());
        return insert;
    }

    /**
     * @param target The object to remove. Should not be modified, else risk of heap pollution occurs.
     * @return PreparedStatement
     * @throws SQLException SQL Error
     */
    @Override
    protected PreparedStatement convertDelete(Flight target) throws SQLException {
        if (delete == null) {
            delete = this.connection.prepareStatement("DELETE FROM flight WHERE id = ?");
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
    protected PreparedStatement convertUpdate(Flight targetOld, Flight targetNew) throws SQLException {
        assert(targetOld.getId() == targetNew.getId());
        if (update == null) {
            update = this.connection.prepareStatement("UPDATE flight SET route_id=?, airplane_id=?, departure_time=?, reserved_seats=?, seat_price=? WHERE id=?");
        }
        update.setInt(1, targetNew.getRoute().getId());
        update.setInt(2, targetNew.getAirplane().getId());
        update.setTimestamp(3, Timestamp.valueOf(targetNew.getDeparture_time()));
        update.setInt(4, targetNew.getReserved_seats());
        update.setFloat(5, targetNew.getSeat_price());
        update.setInt(6, targetOld.getId());
        return update;
    }

    @Override
    public void commit() throws SQLException {
        airportDAO.commit();
        airplaneDAO.commit();
        super.commit();
    }

    @Override
    public void rollback() throws SQLException {
        airportDAO.rollback();
        airplaneDAO.rollback();
        super.rollback();
    }

    @Override
    public void close() throws SQLException {
        super.close();
        airportDAO.close();
        airplaneDAO.close();
    }

    /**
     * @param id The ID of the flight
     * @return A Route, or null if not found
     * @throws SQLException SQL Error
     */
    public Route routeFromID(int id) throws SQLException {
        if (routeid == null) {
            routeid = connection.prepareStatement("SELECT * FROM route WHERE id = ?");
        }
        routeid.setInt(1, id);
        ResultSet rs = routeid.executeQuery();
        if(rs.next()) {
            return new Route(
                    rs.getInt("id"),
                    this.airportDAO.getFromIATA(rs.getString("origin_id")),
                    this.airportDAO.getFromIATA(rs.getString("destination_id"))
            );
        }
        return null;
    }

    /**
     * Insert an entry into the database using direct values
     * @param route_id The ID of the route
     * @param airplane_id The ID of the airplane
     * @param departure_time The departure time
     * @param reserved_seats The number of seats reserved
     * @param seat_prices The prices of seats
     * @throws SQLException Failed to execute query
     */
    public void insertDirect(int route_id, int airplane_id, LocalDateTime departure_time, int reserved_seats, float seat_prices) throws SQLException {
        if (insert == null) {
            insert = this.connection.prepareStatement("INSERT INTO flight (route_id, airplane_id, departure_time, reserved_seats, seat_price) VALUES (?, ?, ?, ?, ?)");
        }
        insert.setInt(1, route_id);
        insert.setInt(2, airplane_id);
        insert.setTimestamp(3, Timestamp.valueOf(departure_time));
        insert.setInt(4, reserved_seats);
        insert.setFloat(5, seat_prices);
        insert.executeUpdate();
    }

    /**
     * @return ArrayList of all Flights
     * @throws SQLException SQL Error
     */
    public ArrayList<Flight> getAll() throws SQLException {
        if (all == null) {
            all = connection.prepareStatement("SELECT * FROM flight;");
        }
        return select(all);
    }


    /**
     * @param origin The origin IATA ID
     * @param destination The destination IATA ID
     * @return The ID if found, -1 if not found (all IDs are non-negative)
     * @throws SQLException SQL Error
     */
    public int routeIDFromIATAs(String origin, String destination) throws SQLException {
        if (routeiatapair == null) {
            routeiatapair = connection.prepareStatement("SELECT * FROM route WHERE destination_id=? AND origin_id=?");
        }
        routeiatapair.setString(1, destination);
        routeiatapair.setString(2, origin);
        ResultSet rs = routeiatapair.executeQuery();
        if(rs.next()) {
            return rs.getInt("id");
        } else {
            return -1;
        }
    }

    /**
     * @param origin The origin IATA
     * @param destination The destination IATA
     * @return The ID of the new route. May return -1 on failure.
     * @throws SQLException SQL Error
     */
    public int insertNewRoute(String origin, String destination) throws SQLException {
        if (newroute == null) {
            newroute = connection.prepareStatement("INSERT INTO route (origin_id, destination_id) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        }
        newroute.setString(1, origin);
        newroute.setString(2, destination);

        Airport origin_air = airportDAO.getFromIATA(origin);
        Airport dest_air = airportDAO.getFromIATA(destination);
        if(origin_air == null || dest_air == null) {
            return -1;
        }

        newroute.executeUpdate();

        ResultSet keys = newroute.getGeneratedKeys();

        if(keys.next()) {
            return keys.getInt(1);
        }
        return -1;
    }

    public Flight getFromID(int id) throws SQLException {
        if (fromid == null) {
            fromid = connection.prepareStatement("SELECT * FROM flight WHERE id=?");
        }
        fromid.setInt(1, id);
        ResultSet rs = fromid.executeQuery();

        if(rs.next()) {
            return convertTo(rs);
        }
        return null;
    }

    public FlightSeats getFlightSeats(int id) throws SQLException {
        if (seats == null) {
            seats = connection.prepareStatement("SELECT * FROM flight_seats WHERE flight_id=?");
        }
        seats.setInt(1, id);
        ResultSet rs = seats.executeQuery();
        if(rs.next()) {
            return new FlightSeats(rs.getInt("flight_id"), rs.getInt("first"),
                    rs.getInt("business"), rs.getInt("economy"));
        }
        return null;
    }

    public void insertFlightSeats(int id, int first, int business, int economy) throws SQLException {
        if (insertSeats == null) {
            insertSeats = connection.prepareStatement("INSERT INTO flight_seats (flight_id, first, business, economy) " +
                    "VALUES (?, ?, ?, ?)");
        }
        insertSeats.setInt(1, id);
        insertSeats.setInt(2, first);
        insertSeats.setInt(3, business);
        insertSeats.setInt(4, economy);
        insertSeats.executeUpdate();
    }

    public void updateFlightSeats(int id, int first, int business, int economy) throws SQLException {
        if (updateFlightSeats == null) {
            updateFlightSeats = connection.prepareStatement("UPDATE flight_seats SET first=?, business=?, economy=? where  flight_id=?");
        }
        updateFlightSeats.setInt(4, id);
        updateFlightSeats.setInt(1, first);
        updateFlightSeats.setInt(2, business);
        updateFlightSeats.setInt(3, economy);
        updateFlightSeats.executeUpdate();
        return;
    }
}
