package com.smoothstack.corejava.dayone;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class GuessingGame {
    /**
     * A random number guessing game.
     *
     * @param args Ignored
     * @author Joshua Podhola
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //Exclusive of top bound
        int rng = ThreadLocalRandom.current().nextInt(1, 101);
        System.out.println("Welcome to our guessing game... Try to get within ten of this random number in five guesses!");
        System.out.println("The number must be between 1 and 100.");
        System.out.println("If you know the strategy, this should be very easy...");
        int guessesRemaining = 5;
        int guess;
        do {
            System.out.println("You have " + guessesRemaining + " remaining. What do you guess?");
            try {
                guess = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("That is not a number. Try again.");
                scanner.next();
                guessesRemaining++; // It works, shut up
                continue;
            } catch (NoSuchElementException | IllegalStateException e) {
                System.out.println("An irrecoverable error occurred. Exiting...");
                break;
            }

            if (guess < 1 || guess > 100) {
                System.out.println("Number must be within 1 and 100.");
                guessesRemaining++;
            } else if (Math.abs(rng - guess) <= 10) {
                System.out.println("Close enough! The number was: " + rng);
                break;
            } else {
                System.out.println("Incorrect!");
            }
        } while (--guessesRemaining > 0);  // Prefix decrement because I am a horrible (lazy) person.
        if (guessesRemaining == 0) {
            System.out.println("You are out of guesses. You have disappointed me.");
        }
    }
}
