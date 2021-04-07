package com.smoothstack.corejava.daytwo;

/**
 * Sum the arguments passed to main() and print the result.
 */
public class SumArguments {
    public static void main(String[] args) {
        Integer total = 0;
        if(args.length != 0) {
            for (String arg : args) {
                try {
                    total += Integer.parseInt(arg); //Total doesn't get modified in the event of an error
                } catch(NumberFormatException e) {
                    System.out.println(arg + " is not an integer.");
                }
            }
            System.out.println("The total is: " + total);
        } else {
            System.out.println("No arguments given.");
        }
    }
}
