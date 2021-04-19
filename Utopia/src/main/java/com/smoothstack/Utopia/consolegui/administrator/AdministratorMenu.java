package com.smoothstack.Utopia.consolegui.administrator;

import com.smoothstack.Utopia.services.*;
import com.smoothstack.Utopia.util.Choices;

public class AdministratorMenu {
    public static void AdminMenu() {
        while(true) {
            switch (Choices.getNumericalChoice("Choose one of the following:\n" +
                    "1) Add/Update/Delete/Read Flights\n" +
                    "2) Add/Update/Delete/Read Airplanes and Airplane Types\n" +
                    "3) Add/Update/Delete/Read Bookings and Passengers\n" +
                    "4) Add/Update/Delete/Read Airports\n" +
                    "5) Add/Update/Delete/Read Users\n" +
                    "6) Exit Menu", 6)) {
                case 1:
                    crud_flights();
                    break;
                case 2:
                    crud_planes();
                    break;
                case 3:
                    crud_bookings();
                    break;
                case 4:
                    crud_airports();
                    break;
                case 5:
                    crud_users();
                    break;
                case 6:
                    return;
            }
        }
    }

    private static void crud_flights() {
        switch(Choices.getNumericalChoice("Choose one of the following:\n" +
                "1) List All\n" +
                "2) Create New\n" +
                "3) Update Entry\n" +
                "4) Remove Entry\n", 4)) {
            case 1:
                ManageFlights.listAll();
                break;
            case 2:
                ManageFlights.createNew();
                break;
            case 3:
                ManageFlights.updateExisting();
                break;
            case 4:
                ManageFlights.deleteFlight();
                break;
        }
    }

    private static void crud_planes() {
        switch(Choices.getNumericalChoice("Choose one of the following:\n" +
                "1) List All\n" +
                "2) Create New\n" +
                "3) Update Entry\n" +
                "4) Remove Entry\n", 4)) {
            case 1:
                ManagePlanes.listAll();
                break;
            case 2:
                ManagePlanes.createNew();
                break;
            case 3:
                ManagePlanes.updateExisting();
                break;
            case 4:
                ManagePlanes.delete();
                break;
        }
    }

    private static void crud_bookings() {
        switch(Choices.getNumericalChoice("Choose one of the following:\n" +
                "1) List All Bookings\n" +
                "2) List All Passengers For Booking\n" +
                "3) Create New Booking\n" +
                "4) Create New Passenger for Booking\n" +
                "5) Update Booking\n" +
                "6) Update Passenger\n" +
                "7) Remove Booking\n" +
                "8) Remove Passenger\n" +
                "9) Attach Flight\n" +
                "10) Detach Flight\n" +
                "11) List Flights", 11)) {
            case 1:
                ManageBookings.listAll();
                break;
            case 2:
                ManageBookings.listPassengers();
                break;
            case 3:
                ManageBookings.createNewBooking();
                break;
            case 4:
                ManageBookings.createNewPassenger();
                break;
            case 5:
                ManageBookings.updateExistingBooking();
                break;
            case 6:
                ManageBookings.updateExistingPassenger();
                break;
            case 7:
                ManageBookings.deleteBooking();
                break;
            case 8:
                ManageBookings.deletePassenger();
                break;
            case 9:
                ManageBookings.attachFlight();
                break;
            case 10:
                ManageBookings.detachFlight();
                break;
            case 11:
                ManageBookings.listFlights();
                break;
        }
    }

    private static void crud_airports() {
        switch(Choices.getNumericalChoice("Choose one of the following:\n" +
                "1) List All\n" +
                "2) Create New\n" +
                "3) Update Entry\n" +
                "4) Remove Entry\n", 4)) {
            case 1:
                ManageAirports.listAll();
                break;
            case 2:
                ManageAirports.createNew();
                break;
            case 3:
                ManageAirports.updateExisting();
                break;
            case 4:
                ManageAirports.delete();
                break;
        }
    }

    private static void crud_users() {
        switch(Choices.getNumericalChoice("Choose one of the following:\n" +
                "1) List All\n" +
                "2) Create New\n" +
                "3) Update Entry\n" +
                "4) Remove Entry\n", 4)) {
            case 1:
                ManageUsers.listAll();
                break;
            case 2:
                ManageUsers.createNew();
                break;
            case 3:
                ManageUsers.updateExisting();
                break;
            case 4:
                ManageUsers.delete();
                break;
        }
    }
}
