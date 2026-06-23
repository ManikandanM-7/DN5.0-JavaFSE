package com.cognizant.designpatterns.factory;

public class Triangle implements Shape {

    private double base;
    private double height;

    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    @Override
    public void draw() {
        System.out.println("Drawing triangle base=" + base + " height=" + height);
    }

    @Override
    public double area() {
        return 0.5 * base * height;
    }
}
