package com.smoothstack.Utopia.consolegui;

import static com.smoothstack.Utopia.util.Choices.*;

import java.util.ArrayList;

public class ConsoleGUI {
    public static void main(String[] args) {
        ArrayList<String> choices = new ArrayList<>();
        choices.add("A Travel Agent");
        choices.add("A User");
        choices.add("An Administrator");
        choices.add("Wanting to Leave");
        while(true){
            switch (getNumericalChoice("Welcome to Utopia Airlines! You are...", choices)) {
                case 1:
                    EmployeeGUI.EmployeeMainMenu();
                    break;
                case 2:
                    System.out.println(2);
                    break;
                case 3:
                    AdministratorGUI.AdministratorMainMenu();
                    break;
                case 4:
                    return;
            }
        }
    }
}
