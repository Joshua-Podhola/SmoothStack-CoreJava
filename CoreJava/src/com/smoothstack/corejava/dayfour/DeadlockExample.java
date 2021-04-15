package com.smoothstack.corejava.dayfour;

public class DeadlockExample {
    public static void main(String[] args) {
        Integer i = 1, j = 2;
        Thread t1 = new Thread(() -> {
            synchronized (i) {
                try {
                    Thread.sleep(100);
                    synchronized (j) {
                        System.out.println("oops");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (j) {
                try {
                    Thread.sleep(100);
                    synchronized (i) {
                        System.out.println("OOPS");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("Begin example.");
        t2.start();
        t1.start();
        System.out.println("End example.");
    }
}
