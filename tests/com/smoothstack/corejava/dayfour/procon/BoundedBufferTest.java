package com.smoothstack.corejava.dayfour.procon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoundedBufferTest {

    static BoundedBuffer buffer;

    @BeforeEach
    void setUp() {
        buffer = BoundedBuffer.getInstance();
    }

    @Test
    void produceAndConsume() {
        buffer.produce("Payload string");
        String result = buffer.consume();
        assertEquals(result, "Payload string");
    }

    @Test
    void getInstance() {
        assertNotNull(buffer);
    }
}