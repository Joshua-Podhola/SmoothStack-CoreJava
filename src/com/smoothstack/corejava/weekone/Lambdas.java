package com.smoothstack.corejava.weekone;

import java.util.function.Function;

public class Lambdas {
    /**
     * Return a lambda to test for oddness
     * @return Lambda
     */
    public static Function<Integer, Boolean> isOdd() {
        return integer -> integer % 2 == 1;
    }

    /**
     * Return a lambda to test for primeness
     * @return Lambda
     */
    public static Function<Integer, Boolean> isPrime() {
        return integer -> {
            if (integer <= 1) return false;
            for (int i = 2; i < integer; i++) if (integer % i == 0) return false;
            return true;
        };
    }

    /**
     * Return a lambda to test for palindrome-ness
     * @return Lambda
     */
    public static Function<String, Boolean> isPalindrome() {
        return s -> {
            for(int i = 0; i < s.length()/2; i++) if(s.charAt(i) != s.charAt(s.length()-i-1)) return false;
            return true;
        };
    }
}
