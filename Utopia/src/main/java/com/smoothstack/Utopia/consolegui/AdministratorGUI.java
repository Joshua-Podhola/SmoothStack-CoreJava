package com.smoothstack.Utopia.consolegui;

import java.util.ArrayList;

import static com.smoothstack.Utopia.util.Choices.getNumericalChoice;

public class AdministratorGUI {
    public static void AdministratorMainMenu() {
        ArrayList<String> choices = new ArrayList<>();
        choices.add("Manage Flights");
        choices.add("Manage Bookings");
        choices.add("Go Back");
        while(true){
            switch (getNumericalChoice("What would you like to do?", choices)) {
                case 1:
                    ManageFlights.ManageFlightsMainMenu();
                    break;
                case 2:
                    ManageBookings.ManageBookingsMainMenu();
                case 3:
                    return;
            }
        }
    }
}
