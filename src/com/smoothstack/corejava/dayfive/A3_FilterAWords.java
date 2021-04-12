package com.smoothstack.corejava.dayfive;

import java.util.Arrays;

public class A3_FilterAWords {
    /**
     * Filter out only words starting with lowercase "a" and three letters long
     * @param input A list of strings
     * @return A list of three-letter strings starting with "a"
     */
    public static String[] FilterAWords(String[] input) {
        return Arrays.stream(input).filter(s -> s.startsWith("a") && s.length() == 3).toArray(String[]::new);
    }
}
