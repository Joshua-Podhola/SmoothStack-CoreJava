package com.smoothstack.corejava.daytwo.shapes;

public interface Shape {
    double calculateArea();
    default void display() {
        System.out.println(this.calculateArea());
    }
}
