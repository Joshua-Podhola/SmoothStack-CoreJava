package com.smoothstack.corejava.daytwo.shapes;

public class Rectangle implements Shape {
    private final double width, height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    @Override
    public double calculateArea() {
        return this.width * this.height;
    }
}
