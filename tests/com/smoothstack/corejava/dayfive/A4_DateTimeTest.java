package com.smoothstack.corejava.dayfive;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class A4_DateTimeTest {

    @Test
    void monthLengthsTest() {
        int[] out = A4_DateTime.monthLengths(2021);
        assertArrayEquals(out, new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31});
    }

    @Test
    void listMondaysTest() {
        LocalDate[] out = A4_DateTime.listMondays(Month.APRIL);
        assertArrayEquals(out, new LocalDate[] {
                LocalDate.of(2021, 4, 5),
                LocalDate.of(2021, 4, 12),
                LocalDate.of(2021, 4, 19),
                LocalDate.of(2021, 4, 26),
                null});
    }

    @Test
    void noFreddyNoTest() {
        assertFalse(A4_DateTime.noFreddyNo(LocalDate.of(1990, 1, 1)));
        assertTrue(A4_DateTime.noFreddyNo(LocalDate.of(2021, 8, 13)));
    }
}