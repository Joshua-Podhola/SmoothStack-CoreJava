package com.smoothstack.corejava.dayfour.procon;

public class ProducerThread extends Thread {
    public void run() {
        BoundedBuffer buffer = BoundedBuffer.getInstance();
        for(int i = 1; i <= 20; i++) {
            buffer.produce(String.format("String #%d", i));
        }
    }
}
