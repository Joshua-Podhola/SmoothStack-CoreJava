package com.smoothstack.Utopia.data.flights;

import com.smoothstack.Utopia.data.bookings.Booking;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents information about a particular flight.
 */
public class Flight implements Serializable {
    private static final long serialVersionUID = 290423172623782466L;

    private final int id;
    private Route route;
    private Airplane airplane;
    private LocalDateTime departure_time;
    private int reserved_seats;
    private Collection<Booking> bookings;
    private float seat_price;
    private FlightSeats flightSeats;

    /**
     * @param departure_time The date and time of departure
     * @param reserved_seats The number of seats reserved
     * @param seat_price     The price of a seat
     */
    public Flight(int id, Route route, Airplane airplane, LocalDateTime departure_time, int reserved_seats, float seat_price) {
        this.id = id;
        this.route = route;
        this.airplane = airplane;
        this.departure_time = departure_time;
        this.reserved_seats = reserved_seats;
        this.seat_price = seat_price;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public LocalDateTime getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(LocalDateTime departure_time) {
        this.departure_time = departure_time;
    }

    public int getReserved_seats() {
        return reserved_seats;
    }

    public void setReserved_seats(int reserved_seats) {
        this.reserved_seats = reserved_seats;
    }

    public float getSeat_price() {
        return seat_price;
    }

    public void setSeat_price(float seat_price) {
        this.seat_price = seat_price;
    }

    public int getId() {
        return id;
    }

    public Collection<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Collection<Booking> bookings) {
        this.bookings = bookings;
    }

    public FlightSeats getFlightSeats() {
        return flightSeats;
    }

    public void setFlightSeats(FlightSeats flightSeats) {
        this.flightSeats = flightSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return id == flight.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("%s: Flight %s -> %s Seats %d/%d%n",
                getDeparture_time().toString(),
                getRoute().getOrigin().getCity(),
                getRoute().getDestination().getCity(),
                getReserved_seats(),
                getAirplane().getType().getCapacity()
        );
    }
}
