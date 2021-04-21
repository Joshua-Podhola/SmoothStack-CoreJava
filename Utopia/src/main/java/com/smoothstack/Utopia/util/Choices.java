package com.smoothstack.Utopia.util;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Choices {


    public static int getNumericalChoice(String prompt, Collection<String> choices) {
        System.out.println(prompt);
        int i = 1;
        for(String t: choices) {
            System.out.printf("%d) %s%n", i, t);
            i++;
        }
        Scanner scanner = new Scanner(System.in);
        int choice;
        String response;
        do {
            response = scanner.nextLine();
            try {
                choice = Integer.parseInt(response);
            } catch (NumberFormatException e) {
                choice = 0;
            }
            if (choice > choices.size() || choice <= 0) {
                System.out.printf("Error: Invalid choice %s\n", response);
            }
        } while (choice > choices.size() || choice <= 0);

        return choice;
    }

    public static int getIntegerInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(prompt);
        Integer choice;
        String response;
        do {
            response = scanner.nextLine();
            try {
                choice = Integer.parseInt(response);
            } catch (NumberFormatException e) {
                System.out.printf("Error: Invalid choice %s. Was expecting an integer.\n", response);
                choice = null;
            }
        } while (choice == null);
        return choice;
    }

    public static float getFloatInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(prompt);
        Float choice;
        String response;
        do {
            response = scanner.nextLine();
            try {
                choice = Float.parseFloat(response);
            } catch (NumberFormatException e) {
                System.out.printf("Error: Invalid choice %s. Was expecting a number.\n", response);
                choice = null;
            }
        } while (choice == null);
        return choice;
    }

    public static boolean getBoolInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(prompt);
        System.err.println("Yes/No");
        String response;
        do {
            response = scanner.nextLine().toLowerCase(Locale.ROOT);
            if (!response.equals("yes") && !response.equals("no")) {
                System.out.println("Expecting yes or no.");
            }
        } while (!response.equals("yes") && !response.equals("no"));
        return response.equals("yes");
    }

    public static String getStringInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(prompt);
        //There's really no error checking to do here
        return scanner.nextLine();
    }

    public static String getIATAInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(prompt);
        String response;
        do {
            response = scanner.nextLine();
            if (response.length() != 3) {
                System.out.println("IATA codes must have a length of exactly 3 characters.");
            }
        } while (response.length() != 3);
        return response;
    }

    public static LocalDateTime getDateTimeInput(String prompt) {
        System.out.println(prompt);
        return LocalDateTime.of(getIntegerInput("Year:"), getIntegerInput("Month:"), getIntegerInput("Day:"),
                getIntegerInput("Hour:"), getIntegerInput("Minute:"));
    }

    public static LocalDate getDateInput(String prompt) {
        System.out.println(prompt);
        return LocalDate.of(getIntegerInput("Year:"), getIntegerInput("Month:"), getIntegerInput("Day:"));
    }

    public static <T> T getChoiceFromList(String prompt, ArrayList<T> list) {
        System.out.println(prompt);
        int i = 1;
        for(T t: list) {
            System.out.printf("%d) %s%n", i, t.toString());
            i++;
        }
        Scanner scanner = new Scanner(System.in);
        int choice;
        String response;
        do {
            response = scanner.nextLine();
            try {
                choice = Integer.parseInt(response);
            } catch (NumberFormatException e) {
                choice = 0;
            }
            if (choice > list.size() || choice <= 0) {
                System.out.printf("Error: Invalid choice %s\n", response);
            }
        } while (choice > list.size() || choice <= 0);

        return list.get(choice - 1);
    }

    /**
     * I didn't feel like making this myself
     * Original: https://stackoverflow.com/questions/20536566/creating-a-random-string-with-a-z-and-0-9-in-java
     * @author Suresh Atta (Mofidied)
     * @return Random string
     */
    public static String getSaltString(int maxLength) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!?()=-~.,/[];:1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < maxLength) {
            int index = rnd.nextInt() * SALTCHARS.length();
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }
}
