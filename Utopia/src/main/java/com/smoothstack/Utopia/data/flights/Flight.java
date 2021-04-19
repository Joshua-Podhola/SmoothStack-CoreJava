package com.smoothstack.Utopia.data.flights;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents information about a particular flight.
 */
public class Flight implements Serializable {
    private static final long serialVersionUID = 290423172623782466L;

    private int id;
    private Route route;
    private Airplane airplane;
    private LocalDateTime departure_time;
    private int reserved_seats;
    private float seat_price;

    /**
     * @param route The route the flight takes
     * @param airplane The airplane used to fly
     * @param departure_time The date and time of departure
     * @param reserved_seats The number of seats reserved
     * @param seat_price The price of a seat
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

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return id == flight.id && reserved_seats == flight.reserved_seats && Float.compare(flight.seat_price, seat_price) == 0 && Objects.equals(route, flight.route) && Objects.equals(airplane, flight.airplane) && Objects.equals(departure_time, flight.departure_time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, route, airplane, departure_time, reserved_seats, seat_price);
    }

    @Override
    public String toString() {
        return String.format("%d) %S -> %S @ %s Serving %d/%d for $%.2f", id, route.getOrigin().get_iata(),
                route.getDestination().get_iata(), departure_time.toString(), reserved_seats, airplane.getType().getCapacity(), seat_price);
    }
}
