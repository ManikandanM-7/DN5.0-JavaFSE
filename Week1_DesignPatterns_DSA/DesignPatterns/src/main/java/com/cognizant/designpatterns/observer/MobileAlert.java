package com.cognizant.designpatterns.observer;

import java.util.ArrayList;
import java.util.List;

// observer pattern - stock price notifications
// when price changes, all registered observers get notified

interface StockObserver {
    void update(String symbol, double price);
}

interface StockSubject {
    void registerObserver(StockObserver o);
    void removeObserver(StockObserver o);
    void notifyObservers();
}

class StockMarket implements StockSubject {

    private List<StockObserver> observers = new ArrayList<>();
    private String symbol;
    private double price;

    public StockMarket(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    @Override
    public void registerObserver(StockObserver o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(StockObserver o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (StockObserver o : observers) {
            o.update(symbol, price);
        }
    }

    public void setPrice(double newPrice) {
        System.out.println("\n" + symbol + ": " + price + " -> " + newPrice);
        this.price = newPrice;
        notifyObservers();
    }
}

class Investor implements StockObserver {
    private String name;
    private double threshold;

    public Investor(String name, double threshold) {
        this.name = name;
        this.threshold = threshold;
    }

    @Override
    public void update(String symbol, double price) {
        System.out.println("[" + name + "] " + symbol + " = " + price +
            (price < threshold ? " !! below threshold" : ""));
    }
}

public class MobileAlert implements StockObserver {
    private String device;

    public MobileAlert(String device) {
        this.device = device;
    }

    @Override
    public void update(String symbol, double price) {
        System.out.println("[push -> " + device + "] " + symbol + " now " + price);
    }

    public static void main(String[] args) {
        StockMarket tcs = new StockMarket("TCS", 3500.0);

        tcs.registerObserver(new Investor("Mani", 3400.0));
        tcs.registerObserver(new Investor("Nithish", 3300.0));
        tcs.registerObserver(new MobileAlert("iPhone-Mani"));

        tcs.setPrice(3450.0);
        tcs.setPrice(3350.0);
        tcs.setPrice(3280.0);
    }
}
