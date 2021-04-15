package com.smoothstack.corejava.daytwo.shapes;

public class Triangle implements Shape {
    private final double width, height;

    public Triangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return (this.width * this.height)/2;
    }
}
