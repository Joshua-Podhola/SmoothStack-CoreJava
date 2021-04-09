package com.smoothstack.corejava.dayfour.procon;

public class Main {
    public static void main(String[] args) {
        Thread producer = new ProducerThread();
        Thread consumer = new ConsumerThread();
        producer.start();
        consumer.start();
        System.out.println("Both threads started!");
    }
}
