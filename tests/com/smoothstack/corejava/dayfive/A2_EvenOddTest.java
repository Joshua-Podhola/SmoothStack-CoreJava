package com.smoothstack.corejava.dayfive;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class A2_EvenOddTest {

    @Test
    void evenOddTest() {
        assertEquals(A2_EvenOdd.evenOdd(new Integer[]{0, 1, 2, 3}), "e0, o1, e2, o3");
        assertEquals(A2_EvenOdd.evenOdd(new Integer[]{1, 0, -1, -2}), "o1, e0, o-1, e-2");
    }
}