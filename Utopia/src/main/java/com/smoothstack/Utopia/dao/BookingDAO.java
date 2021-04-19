package com.smoothstack.Utopia.dao;

import com.smoothstack.Utopia.data.bookings.Booking;
import com.smoothstack.Utopia.data.bookings.FlightBooking;
import com.smoothstack.Utopia.data.bookings.Passenger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class BookingDAO extends BaseDAO<Booking> {
    FlightsDAO flightsDAO;
    /**
     * Initializes the internal connection ready for use
     *
     * @throws SQLException           The connection could not be made.
     * @throws ClassNotFoundException The database driver could not be loaded.
     */
    public BookingDAO() throws SQLException, ClassNotFoundException {
        super();
        flightsDAO = new FlightsDAO();
    }
    PreparedStatement insert, delete, update, allPassengers, insertPassenger, bookingFromID, passengerFromID, all,
            updatePassenger, deletePassenger, insertFlightBooking, deleteFlightBooking, listFlightBookings,
            getFlightBooking, agentBookings;

    /**
     * @param rs The result set
     * @return A Booking
     * @throws SQLException Could not get from resultset
     */
    @Override
    protected Booking convertTo(ResultSet rs) throws SQLException {
        return new Booking(
                rs.getInt("id"),
                rs.getBoolean("is_active"),
                rs.getString("confirmation_code")
        );
    }

    /**
     * Insert the object into the database. ID is ignored and auto-assigned to new value.
     * @param target The object to add. Should not be modified, else risk of heap pollution occurs.
     * @return A PreparedStatement to insert a single object
     * @throws SQLException Could not insert
     */
    @Override
    protected PreparedStatement convertInsert(Booking target) throws SQLException {
        if (insert == null) {
            insert = this.connection.prepareStatement("INSERT INTO booking (is_active, confirmation_code) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        }
        insert.setBoolean(1, target.isActive());
        insert.setString(2, target.getConfirmationCode());
        return insert;
    }

    /**
     * @param target The object to remove. Should not be modified, else risk of heap pollution occurs.
     * @return PreparedStatement
     * @throws SQLException SQL Error
     */
    @Override
    protected PreparedStatement convertDelete(Booking target) throws SQLException {
        if (delete == null) {
            delete = this.connection.prepareStatement("DELETE FROM booking WHERE id = ?");
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
    protected PreparedStatement convertUpdate(Booking targetOld, Booking targetNew) throws SQLException {
        assert(targetOld.getId() == targetNew.getId());
        if (update == null) {
            update = this.connection.prepareStatement("UPDATE booking SET is_active=?, confirmation_code=? WHERE id=?");
        }
        update.setBoolean(1, targetNew.isActive());
        update.setString(2, targetNew.getConfirmationCode());
        update.setInt(3, targetOld.getId());
        return update;
    }

    @Override
    public void close() throws SQLException {
        super.close();
        flightsDAO.close();
    }

    /**
     * Enters a booking directly.
     * @throws SQLException SQL Error
     * @return id
     */
    public int insertDirect(boolean is_active, String confirmation_code) throws SQLException {
        if (insert == null) {
            insert = this.connection.prepareStatement("INSERT INTO booking (is_active, confirmation_code) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        }
        insert.setBoolean(1, is_active);
        insert.setString(2, confirmation_code);
        insert.executeUpdate();
        ResultSet keys = insert.getGeneratedKeys();
        if(keys.next()) {
            return keys.getInt(1);
        }
        return -1;
    }


    /**
     * Convert resultset and booking to passenger
     * @param rs ResultSet
     * @param booking Booking
     * @return Passenger
     * @throws SQLException SQL Error
     */
    protected Passenger passengerConvertTo(ResultSet rs, Booking booking) throws SQLException {
        return new Passenger(rs.getInt("id"), booking, rs.getString("given_name"),
                rs.getString("family_name"), rs.getString("gender"),
                rs.getString("address"), rs.getDate("dob").toLocalDate());
    }

    /**
     * Convert resultset directly to passenger
     * @param rs ResultSet
     * @return Passenger
     * @throws SQLException SQL Error
     */
    protected Passenger passengerConvertTo(ResultSet rs) throws SQLException {
        return new Passenger(rs.getInt("id"), bookingFromID(rs.getInt("booking_id")), rs.getString("given_name"),
                rs.getString("family_name"), rs.getString("gender"),
                rs.getString("address"), rs.getDate("dob").toLocalDate());
    }

    /**
     * @param booking Booking
     * @return List of passengers
     * @throws SQLException SQL Error
     */
    public ArrayList<Passenger> getAllPassengers(Booking booking) throws SQLException {
        if (allPassengers == null) {
            allPassengers = connection.prepareStatement("SELECT * FROM passenger WHERE booking_id=?");
        }
        allPassengers.setInt(1, booking.getId());
        ResultSet rs = allPassengers.executeQuery();
        ArrayList<Passenger> passengers = new ArrayList<>();
        while (rs.next()) {
            passengers.add(passengerConvertTo(rs, booking));
        }

        return passengers;
    }

    /**
     * Directly add passenger
     * @param booking Booking of passenger
     * @param given_name Given name
     * @param family_name Family name
     * @param gender Gender
     * @param address Address
     * @param dob Date of Birth
     * @return Passenger ID of new passenger, or -1 on failure.
     * @throws SQLException SQL Error
     */
    public int addPassengerDirect(Booking booking, String given_name, String family_name, String gender,
                                   String address, LocalDate dob) throws SQLException {
        if (insertPassenger == null) {
            insertPassenger = connection.prepareStatement("INSERT INTO passenger (booking_id, given_name, " +
                    "family_name, dob, gender, address) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        }

        insertPassenger.setInt(1, booking.getId());
        insertPassenger.setString(2, given_name);
        insertPassenger.setString(3, family_name);
        insertPassenger.setDate(4, Date.valueOf(dob));
        insertPassenger.setString(5, gender);
        insertPassenger.setString(6, address);
        insertPassenger.executeUpdate();

        ResultSet keys = insertPassenger.getGeneratedKeys();

        if(keys.next()) {
            return keys.getInt(1);
        }
        return -1;
    }

    /**
     * @param id ID of booking
     * @return Booking
     * @throws SQLException SQL Error
     */
    public Booking bookingFromID(int id) throws SQLException {
        if (bookingFromID == null) {
            bookingFromID = connection.prepareStatement("SELECT * FROM booking WHERE ID=?");
        }
        bookingFromID.setInt(1, id);
        ResultSet rs = bookingFromID.executeQuery();
        if(rs.next()) {
            return convertTo(rs);
        }
        return null;
    }

    /**
     * @param id ID of passenger
     * @return Passenger
     * @throws SQLException SQL Error
     */
    public Passenger passengerFromID(int id) throws SQLException {
        if (passengerFromID == null) {
            passengerFromID = connection.prepareStatement("SELECT * FROM passenger WHERE ID=?");
        }
        passengerFromID.setInt(1, id);
        ResultSet rs = passengerFromID.executeQuery();
        if(rs.next()) {
            return passengerConvertTo(rs);
        }
        return null;
    }

    public ArrayList<Booking> getAll() throws SQLException {
        if (all == null) {
            all = connection.prepareStatement("SELECT * FROM booking;");
        }
        return select(all);
    }

    public void updatePassenger(Passenger targetOld, Passenger targetNew) throws SQLException {
        assert(targetOld.getId() == targetNew.getId());
        if (updatePassenger == null) {
            updatePassenger = this.connection.prepareStatement("UPDATE passenger SET booking_id=?, given_name=?, " +
                    "family_name=?, dob=?, gender=?, address=? WHERE id=?");
        }
        updatePassenger.setInt(1, targetNew.getBooking().getId());
        updatePassenger.setString(2, targetNew.getGiven_name());
        updatePassenger.setString(3, targetNew.getFamily_name());
        updatePassenger.setDate(4, Date.valueOf(targetNew.getDob()));
        updatePassenger.setString(5, targetNew.getGender());
        updatePassenger.setString(6, targetNew.getAddress());
        updatePassenger.setInt(7, targetOld.getId());
        updatePassenger.executeUpdate();
    }

    public void deletePassenger(Passenger target) throws SQLException {
        if (deletePassenger == null) {
            deletePassenger = this.connection.prepareStatement("DELETE FROM passenger WHERE id = ?");
        }
        deletePassenger.setInt(1, target.getId());
        deletePassenger.executeUpdate();
    }

    public void insertFlightBooking(int flight_id, int booking_id) throws SQLException {
        if (insertFlightBooking == null) {
            insertFlightBooking = this.connection.prepareStatement("INSERT INTO flight_bookings (flight_id, booking_id) VALUES (?, ?)");
        }
        insertFlightBooking.setInt(1, flight_id);
        insertFlightBooking.setInt(2, booking_id);
        insertFlightBooking.executeUpdate();
    }

    public void deleteFlightBooking(int flight_id, int booking_id) throws SQLException {
        if (deleteFlightBooking == null) {
            deleteFlightBooking = this.connection.prepareStatement("DELETE FROM flight_bookings WHERE flight_id=? AND booking_id=?");
        }
        deleteFlightBooking.setInt(1, flight_id);
        deleteFlightBooking.setInt(2, booking_id);
        deleteFlightBooking.executeUpdate();
    }

    public ArrayList<FlightBooking> getFlightBookings(int booking_id) throws SQLException, ClassNotFoundException {
        if (listFlightBookings == null) {
            listFlightBookings = this.connection.prepareStatement("SELECT * FROM flight_bookings WHERE booking_id=?");
        }
        listFlightBookings.setInt(1, booking_id);
        ResultSet rs = listFlightBookings.executeQuery();
        ArrayList<FlightBooking> flightBookings = new ArrayList<>();
        while(rs.next()) {
            flightBookings.add(convertToFlightBooking(rs));
        }
        return flightBookings;
    }

    protected FlightBooking convertToFlightBooking(ResultSet rs) throws SQLException, ClassNotFoundException {
        return new FlightBooking(flightsDAO.getFromID(rs.getInt("flight_id")), bookingFromID(rs.getInt("booking_id")));
    }

    public boolean flightBookingExists(int flight_id, int booking_id) throws SQLException {
        if (getFlightBooking == null) {
            getFlightBooking = this.connection.prepareStatement("SELECT * FROM flight_bookings WHERE booking_id=? AND flight_id=?");
        }
        getFlightBooking.setInt(1, booking_id);
        getFlightBooking.setInt(2, flight_id);
        ResultSet rs = getFlightBooking.executeQuery();
        return rs.next();
    }

    public ArrayList<Booking> getAgentBookings(int agent_id) throws SQLException {
        if (agentBookings == null) {
            agentBookings = this.connection.prepareStatement("SELECT b.id as id, b.is_active as is_active, b.confirmation_code as confirmation_code " +
                    "FROM booking_agent ba, booking b " +
                    "WHERE ba.agent_id=? " +
                    "AND ba.booking_id = b.id;");
        }
        agentBookings.setInt(1, agent_id);
        ResultSet rs = agentBookings.executeQuery();
        ArrayList<Booking> bookings = new ArrayList<>();
        while(rs.next()) {
            bookings.add(convertTo(rs));
        }
        return bookings;
    }
}
