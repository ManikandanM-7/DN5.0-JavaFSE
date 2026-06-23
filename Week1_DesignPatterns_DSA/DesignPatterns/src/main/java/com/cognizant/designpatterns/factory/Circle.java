package com.cognizant.designpatterns.factory;

public class Circle implements Shape {

    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public void draw() {
        System.out.println("Drawing circle with radius " + radius);
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}
