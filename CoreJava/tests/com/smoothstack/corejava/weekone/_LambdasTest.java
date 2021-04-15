package com.smoothstack.corejava.weekone;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class _LambdasTest {

    @Test
    void isOddTest() {
        assertFalse(Lambdas.isOdd().apply(2));
        assertTrue(Lambdas.isOdd().apply(1));
    }

    @Test
    void isPrimeTest() {
        assertTrue(Lambdas.isPrime().apply(3));
        assertTrue(Lambdas.isPrime().apply(5));
        assertTrue(Lambdas.isPrime().apply(7));
        assertFalse(Lambdas.isPrime().apply(4));
        assertFalse(Lambdas.isPrime().apply(9));
        assertFalse(Lambdas.isPrime().apply(27));
    }

    @Test
    void isPalindromeTest() {
        assertTrue(Lambdas.isPalindrome().apply("a"));
        assertTrue(Lambdas.isPalindrome().apply("aaa"));
        assertTrue(Lambdas.isPalindrome().apply("baab"));
        assertFalse(Lambdas.isPalindrome().apply("baa"));
        assertFalse(Lambdas.isPalindrome().apply("aab"));
        assertFalse(Lambdas.isPalindrome().apply("baba"));
    }
}