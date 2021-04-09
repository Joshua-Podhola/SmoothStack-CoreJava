package com.smoothstack.corejava.dayfour.procon;

import java.util.ArrayDeque;

public class BoundedBuffer {
    private static BoundedBuffer instance;
    private final ArrayDeque<String> buffer;
    private static final int bufferSize = 5;

    private BoundedBuffer() {
        buffer = new ArrayDeque<>();
    }

    /**
     * Push a string to the buffer
     * @param payload String payload
     */
    synchronized public void produce(String payload) {
        while(buffer.size() >= bufferSize) Thread.yield();
        buffer.push(payload);
    }

    /**
     * Pop a string from the buffer
     * @return String payload
     */
    synchronized public String consume() {
        while(buffer.isEmpty()) Thread.yield();
        return buffer.pop();
    }

    public static BoundedBuffer getInstance() {
        if(instance == null) {
            synchronized (BoundedBuffer.class) {
                if(instance == null) instance = new BoundedBuffer();
            }
        }
        return instance;
    }

}
