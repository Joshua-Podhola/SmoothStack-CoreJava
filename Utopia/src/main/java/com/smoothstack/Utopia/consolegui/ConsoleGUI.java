package com.smoothstack.Utopia.consolegui;

import com.smoothstack.Utopia.consolegui.administrator.AdministratorMenu;
import com.smoothstack.Utopia.consolegui.agent.AgentMenu;
import com.smoothstack.Utopia.consolegui.traveler.TravelerMenu;
import com.smoothstack.Utopia.util.Choices;

public class ConsoleGUI {
    public static void main(String[] args) {
        while(true) {
            int choice = Choices.getNumericalChoice("Welcome to the Utopia Airlines Management System. Which category of a user are you?\n" +
                    "1) Employee\n" +
                    "2) Administrator\n" +
                    "3) Traveller", 3);

            switch (choice) {
                case 1:
                    AgentMenu.EmployeeMenu();
                    break;
                case 2:
                    AdministratorMenu.AdminMenu();
                    break;
                case 3:
                    TravelerMenu.GuestMenu();
                    break;
            }
        }
    }
}
