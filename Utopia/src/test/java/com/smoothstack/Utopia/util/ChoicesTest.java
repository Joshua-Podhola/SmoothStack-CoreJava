package com.smoothstack.Utopia.util;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ChoicesTest {

    @Test
    void getNumericalChoiceTest() {
        assertEquals(1, Choices.getNumericalChoice("...", 3, new ByteArrayInputStream("1\n".getBytes())));
        assertEquals(1, Choices.getNumericalChoice("...", 3, new ByteArrayInputStream("0\n1\n".getBytes())));
        assertEquals(1, Choices.getNumericalChoice("...", 3, new ByteArrayInputStream("4\n1\n".getBytes())));
        assertEquals(1, Choices.getNumericalChoice("...", 3, new ByteArrayInputStream("notanumber\n1\n".getBytes())));
    }

    @Test
    void getIntegerInputTest() {
        assertEquals(1, Choices.getIntegerInput("...", new ByteArrayInputStream("1\n".getBytes())));
        assertEquals(-1, Choices.getIntegerInput("...", new ByteArrayInputStream("-1\n".getBytes())));
        assertEquals(1, Choices.getIntegerInput("...", new ByteArrayInputStream("notanumber\n1\n".getBytes())));
    }

    @Test
    void getFloatInputTest() {
        assertEquals(1f, Choices.getFloatInput("...", new ByteArrayInputStream("1.\n".getBytes())));
        assertEquals(-1f, Choices.getFloatInput("...", new ByteArrayInputStream("-1\n".getBytes())));
        assertEquals(0.5f, Choices.getFloatInput("...", new ByteArrayInputStream("notanumber\n0.5\n".getBytes())));
    }
}