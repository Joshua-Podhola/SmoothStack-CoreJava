package com.smoothstack.Utopia.dao.flights;

import com.smoothstack.Utopia.dao.BaseDAO;
import com.smoothstack.Utopia.data.flights.Airplane;
import com.smoothstack.Utopia.data.flights.Flight;
import com.smoothstack.Utopia.data.flights.Route;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FlightDAO extends BaseDAO<Flight> {
    /**
     * @param connection A database connection. Should be valid.
     */
    public FlightDAO(Connection connection) {
        super(connection);
    }

    public Flight insert(Route route, Airplane airplane, LocalDateTime departure_time, int reserved_seats, float seat_price) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement(
                "INSERT INTO flight (route_id, airplane_id, departure_time, reserved_seats, seat_price) " +
                        "VALUES (?, ?, ?, ?, ?);",
                Statement.RETURN_GENERATED_KEYS
        );

        ps.setInt(1, route.getId());
        ps.setInt(2, airplane.getId());
        ps.setTimestamp(3, Timestamp.valueOf(departure_time));
        ps.setInt(4, reserved_seats);
        ps.setFloat(5, seat_price);
        ps.executeUpdate();

        ResultSet keys = ps.getGeneratedKeys();
        keys.first();
        Flight flight = new Flight(keys.getInt(1), route, airplane, departure_time, reserved_seats, seat_price);
        flight.setRoute(route);
        flight.setAirplane(airplane);
        return flight;
    }

    public Flight getByID(int id) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement("SELECT * FROM flight WHERE id=?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return convert(rs);
        }
        return null;
    }

    public void updateDepartureTime(LocalDateTime newTime) throws SQLException {
        assertHasAssignment();
        PreparedStatement ps = db_connection.prepareStatement("UPDATE flight SET departure_time=? WHERE id = ?");
        ps.setTimestamp(1, Timestamp.valueOf(newTime));
        ps.setInt(2, assigned.getId());
        ps.executeUpdate();
    }

    public void updateSeatPrice(float newPrice) throws SQLException {
        assertHasAssignment();
        PreparedStatement ps = db_connection.prepareStatement("UPDATE flight SET seat_price=? WHERE id = ?");
        ps.setFloat(1, newPrice);
        ps.setInt(2, assigned.getId());
        ps.executeUpdate();
    }

    public void updateRoute(Route route) throws SQLException {
        assertHasAssignment();
        PreparedStatement ps = db_connection.prepareStatement("UPDATE flight SET route_id=? WHERE id = ?");
        ps.setFloat(1, route.getId());
        ps.setInt(2, assigned.getId());
        ps.executeUpdate();
    }

    /**
     * Perform a query to search flights by their route's origin city
     * @param origin The origin city query term to look for
     * @return An ArrayList of Flights
     * @throws SQLException SQL Error
     */
    public ArrayList<Flight> searchByOrigin(String origin) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement("SELECT f.id, f.route_id, f.airplane_id, f.departure_time, f.reserved_seats, f.seat_price " +
                "FROM flight f, route r, airport a " +
                "WHERE f.route_id = r.id " +
                "AND r.origin_id = a.iata_id " +
                "AND a.city LIKE ?");
        ///Yes, as silly as it looks, that is how you're meant to do it.
        ps.setString(1, String.format("%%%s%%", origin));
        ResultSet rs = ps.executeQuery();
        ArrayList<Flight> flights = new ArrayList<>();
        while(rs.next()) {
            flights.add(convert(rs));
        }
        return flights;
    }

    /**
     * Perform a query to search flights by their route's destination city
     * @param destination The destination city query term to look for
     * @return An ArrayList of Flights
     * @throws SQLException SQL Error
     */
    public ArrayList<Flight> searchByDestination(String destination) throws SQLException {
        PreparedStatement ps = db_connection.prepareStatement("SELECT f.id, f.route_id, f.airplane_id, f.departure_time, f.reserved_seats, f.seat_price " +
                "FROM flight f, route r, airport a " +
                "WHERE f.route_id = r.id " +
                "AND r.destination_id = a.iata_id " +
                "AND a.city LIKE ?");
        ///Yes, as silly as it looks, that is how you're meant to do it.
        ps.setString(1, String.format("%%%s%%", destination));
        ResultSet rs = ps.executeQuery();
        ArrayList<Flight> flights = new ArrayList<>();
        while(rs.next()) {
            flights.add(convert(rs));
        }
        return flights;
    }

    @Override
    public void delete() throws SQLException {
        assertHasAssignment();
        //Must delete all FlightBooking and FlightSeats objects first.
        PreparedStatement ps_bookings = db_connection.prepareStatement("DELETE FROM flight_bookings WHERE flight_id=?");
        ps_bookings.setInt(1, assigned.getId());
        ps_bookings.executeUpdate();
        PreparedStatement ps_seats = db_connection.prepareStatement("DELETE FROM flight_seats WHERE flight_id=?");
        ps_seats.setInt(1, assigned.getId());
        ps_seats.executeUpdate();
        PreparedStatement ps = db_connection.prepareStatement("DELETE FROM flight WHERE id=?");
        ps.setInt(1, assigned.getId());
        ps.executeUpdate();
    }

    @Override
    protected Flight convert(ResultSet rs) throws SQLException {
        RouteDAO routeDAO = new RouteDAO(db_connection);
        AirplaneDAO airplaneDAO = new AirplaneDAO(db_connection);
        return new Flight(
                rs.getInt("id"),
                routeDAO.getByID(rs.getInt("route_id")),
                airplaneDAO.getByID(rs.getInt("airplane_id")),
                rs.getTimestamp("departure_time").toLocalDateTime(),
                rs.getInt("reserved_seats"),
                rs.getFloat("seat_price")
                );
    }
}
