package com.smoothstack.corejava.dayfive;

import java.util.Arrays;

public class A2_EvenOdd {
    /**
     * Return a string formatted with the evenness of a list of integers. Treats 0 as even.
     * @param ints An array of Integers
     * @return A string with o## for odds and e## for evens
     */
    public static String evenOdd(Integer[] ints) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(ints).forEach(integer -> sb.append(String.format(integer%2==0 ? "e%d, " : "o%d, ", integer)));
        //Lazily cut out the last two characters
        return sb.substring(0, sb.length() - 2);
    }
}
