package com.smoothstack.Utopia.util;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Scanner;

public class Choices {
    public static int getNumericalChoice(String prompt, int maxChoices) {
        return getNumericalChoice(prompt, maxChoices, System.in);
    }

    public static int getNumericalChoice(String prompt, int maxChoices, InputStream is) {
        Scanner scanner = new Scanner(is);
        System.out.println(prompt);
        int choice;
        String response;
        do {
            response = scanner.nextLine();
            try {
                choice = Integer.parseInt(response);
            } catch(NumberFormatException e) {
                choice = 0;
            }
            if (choice > maxChoices || choice <= 0) {
                System.out.printf("Error: Invalid choice %s\n", response);
            }
        } while(choice > maxChoices || choice <= 0);

        return choice;
    }

    public static int getIntegerInput(String prompt) {
        return getIntegerInput(prompt, System.in);
    }

    public static int getIntegerInput(String prompt, InputStream is) {
        Scanner scanner = new Scanner(is);
        System.out.println(prompt);
        Integer choice;
        String response;
        do {
            response = scanner.nextLine();
            try {
                choice = Integer.parseInt(response);
            } catch(NumberFormatException e) {
                System.out.printf("Error: Invalid choice %s. Was expecting an integer.\n", response);
                choice = null;
            }
        } while(choice == null);
        return choice;
    }

    public static float getFloatInput(String prompt) {
        return getFloatInput(prompt, System.in);
    }

    public static float getFloatInput(String prompt, InputStream is) {
        Scanner scanner = new Scanner(is);
        System.out.println(prompt);
        Float choice;
        String response;
        do {
            response = scanner.nextLine();
            try {
                choice = Float.parseFloat(response);
            } catch(NumberFormatException e) {
                System.out.printf("Error: Invalid choice %s. Was expecting a number.\n", response);
                choice = null;
            }
        } while(choice == null);
        return choice;
    }

    public static boolean getBoolInput(String prompt) {
        return getBoolInput(prompt, System.in);
    }

    public static boolean getBoolInput(String prompt, InputStream is) {
        Scanner scanner = new Scanner(is);
        System.out.println(prompt);
        System.err.println("Yes/No");
        String response;
        do {
            response = scanner.nextLine().toLowerCase(Locale.ROOT);
            if(!response.equals("yes") && !response.equals("no")) {
                System.out.println("Expecting yes or no.");
            }
        } while(!response.equals("yes") && !response.equals("no"));
        return response.equals("yes");
    }

    public static String getStringInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(prompt);
        //There's really no error checking to do here
        return scanner.nextLine();
    }

    public static String getIATAInput(String prompt) {
        return getIATAInput(prompt, System.in);
    }

    public static String getIATAInput(String prompt, InputStream is) {
        Scanner scanner = new Scanner(is);
        System.out.println(prompt);
        String response;
        do {
            response = scanner.nextLine();
            if(response.length() != 3) {
                System.out.println("IATA codes must have a length of exactly 3 characters.");
            }
        } while(response.length() != 3);
        return response;
    }

    public static LocalDateTime getDateTimeInput(String prompt) {
        return getDateTimeInput(prompt, System.in);
    }

    public static LocalDateTime getDateTimeInput(String prompt, InputStream is) {
        System.out.println(prompt);
        return LocalDateTime.of(getIntegerInput("Year:", is), getIntegerInput("Month:", is), getIntegerInput("Day:", is),
                getIntegerInput("Hour:", is), getIntegerInput("Minute:", is));
    }

    public static LocalDate getDateInput(String prompt) {
        System.out.println(prompt);
        return LocalDate.of(getIntegerInput("Year:", System.in), getIntegerInput("Month:", System.in), getIntegerInput("Day:", System.in));
    }
}
