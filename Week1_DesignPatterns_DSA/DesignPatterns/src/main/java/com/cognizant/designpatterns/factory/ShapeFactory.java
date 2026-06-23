package com.cognizant.designpatterns.factory;

// factory decides which shape to create based on input
// client doesn't need to know how each shape is made
public class ShapeFactory {

    public static Shape getShape(String type, double... dims) {
        if (type == null) {
            throw new IllegalArgumentException("type cant be null");
        }

        switch (type.toUpperCase()) {
            case "CIRCLE":
                return new Circle(dims[0]);
            case "RECTANGLE":
                return new Rectangle(dims[0], dims[1]);
            case "TRIANGLE":
                return new Triangle(dims[0], dims[1]);
            default:
                throw new IllegalArgumentException("unknown shape: " + type);
        }
    }
}
