package com.cognizant.designpatterns;

import com.cognizant.designpatterns.singleton.Logger;
import com.cognizant.designpatterns.factory.Shape;
import com.cognizant.designpatterns.factory.ShapeFactory;

public class DesignPatternsMain {

    public static void main(String[] args) {

        // testing singleton - both should be same object
        Logger l1 = Logger.getInstance();
        Logger l2 = Logger.getInstance();
        l1.log("app started");
        System.out.println("same instance? " + (l1 == l2));

        System.out.println();

        // testing factory
        Shape c = ShapeFactory.getShape("CIRCLE", 7.0);
        Shape r = ShapeFactory.getShape("RECTANGLE", 5.0, 10.0);
        Shape t = ShapeFactory.getShape("TRIANGLE", 6.0, 8.0);

        c.draw();
        System.out.println("area: " + c.area());
        r.draw();
        System.out.println("area: " + r.area());
        t.draw();
        System.out.println("area: " + t.area());
    }
}
