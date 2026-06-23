package com.cognizant.designpatterns.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Observer Pattern (Behavioral)
 * Scenario: Stock market notification system where multiple investors want
 * to be notified when a stock price changes.
 * all dependents are notified and updated automatically.
 */

// Observer interface
interface StockObserver {
    void update(String stockSymbol, double newPrice);
}

// Subject interface
interface StockSubject {
    void registerObserver(StockObserver observer);
    void removeObserver(StockObserver observer);
    void notifyObservers();
}

// Concrete Subject — Stock market tracker
class StockMarket implements StockSubject {

    private List<StockObserver> observers = new ArrayList<>();
    private String stockSymbol;
    private double currentPrice;

    public StockMarket(String stockSymbol, double initialPrice) {
        this.stockSymbol  = stockSymbol;
        this.currentPrice = initialPrice;
    }

    @Override
    public void registerObserver(StockObserver observer) {
        observers.add(observer);
        System.out.println("Observer registered for: " + stockSymbol);
    }

    @Override
    public void removeObserver(StockObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (StockObserver observer : observers) {
            observer.update(stockSymbol, currentPrice);
        }
    }

    // Triggers notification on price change
    public void setPrice(double newPrice) {
        System.out.println("\n--- " + stockSymbol + " price changed: " + currentPrice + " → " + newPrice + " ---");
        this.currentPrice = newPrice;
        notifyObservers();
    }
}

// Concrete Observer — Investor
class Investor implements StockObserver {

    private String name;
    private double alertThreshold;

    public Investor(String name, double alertThreshold) {
        this.name             = name;
        this.alertThreshold   = alertThreshold;
    }

    @Override
    public void update(String stockSymbol, double newPrice) {
        System.out.println("[Investor: " + name + "] " + stockSymbol
                + " is now ₹" + newPrice
                + (newPrice < alertThreshold ? " ⚠ Below your threshold of ₹" + alertThreshold : " ✓"));
    }
}

// Concrete Observer — Mobile App Alert
public class MobileAlert implements StockObserver {

    private String deviceId;

    public MobileAlert(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public void update(String stockSymbol, double newPrice) {
        System.out.println("[Mobile Push → " + deviceId + "] ALERT: " + stockSymbol + " = ₹" + newPrice);
    }

    // Demo main
    public static void main(String[] args) {
        StockMarket tcs = new StockMarket("TCS", 3500.00);

        Investor mani      = new Investor("Mani", 3400.00);
        Investor nithish   = new Investor("Nithish", 3300.00);
        MobileAlert mobile = new MobileAlert("iPhone-Mani");

        tcs.registerObserver(mani);
        tcs.registerObserver(nithish);
        tcs.registerObserver(mobile);

        tcs.setPrice(3450.00);
        tcs.setPrice(3350.00);
        tcs.setPrice(3280.00);

        System.out.println("\nNithish removes alert...");
        tcs.removeObserver(nithish);
        tcs.setPrice(3200.00);
    }
}
