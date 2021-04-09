package com.smoothstack.corejava.dayfour;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineTest {

    @Test
    void getSlope() {
        Line line = new Line(1, 1, 4, 4);
        assertEquals(line.getSlope(), 1.0, 0.001);
    }

    @Test
    void getDistance() {
        Line line = new Line(1, 1, 4, 4);
        assertEquals(line.getDistance(), 4.242641, 0.001);
    }

    @Test
    void parallelTo() {
        Line line1 = new Line(1, 1, 4, 4);
        Line line2 = new Line(0, 0, 3, 3);
        Line line3 = new Line(1, 1, 2, 4);

        assertTrue(line1.parallelTo(line2));
        assertFalse(line1.parallelTo(line3));
    }
}