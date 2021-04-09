package com.smoothstack.corejava.dayfour.procon;

public class ConsumerThread extends Thread {
    public void run() {
        BoundedBuffer buffer = BoundedBuffer.getInstance();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            System.out.println("Consumer thread interrupted!");
        }
        for(int i = 0; i != 20; i++) {
            System.out.println(buffer.consume());
            /*
            Because we end up sleeping as the producer rapidly produces more strings, the last strings consumed will
            almost always be the ones as the beginning, and the last amount of them will likely be approximately
            the buffer size minus one, which demonstrates the buffer is working as intended.
             */
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.out.println("Consumer thread interrupted!");
                return;
            }
        }
    }
}
