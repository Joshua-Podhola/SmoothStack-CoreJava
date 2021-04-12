package com.smoothstack.corejava.weekone;

import java.util.Arrays;

public class Functional {
    /**
     * Return the least significant digit of each element
     * @param input Array of integers
     * @return Array of integers 0-9
     */
    public static int[] rightDigit(int[] input) {
        return Arrays.stream(input).map(i -> i % 10).toArray();
    }

    /**
     * Doubles all integers in array
     * @param input Array of integers
     * @return Doubled array
     */
    public static int[] doubling(int[] input) {
        return Arrays.stream(input).map(i -> i * 2).toArray();
    }

    /**
     * Removes all "x"s from all strings
     * @param input Array of strings
     * @return Array of strings with no strings containing any "x"s.
     */
    public static String[] noX(String[] input) {
        return Arrays.stream(input).map(s -> s.replace("x", "")).toArray(String[]::new);
    }
}
