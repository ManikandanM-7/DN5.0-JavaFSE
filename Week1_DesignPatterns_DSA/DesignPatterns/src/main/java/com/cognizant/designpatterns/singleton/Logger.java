package com.cognizant.designpatterns.singleton;

// singleton pattern - only one logger instance should exist in the app
public class Logger {

    private static Logger instance;

    private Logger() {
        System.out.println("Logger created");
    }

    // using synchronized to make it thread safe
    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void log(String msg) {
        System.out.println("[LOG] " + msg);
    }

    public void warn(String msg) {
        System.out.println("[WARN] " + msg);
    }

    public void error(String msg) {
        System.out.println("[ERROR] " + msg);
    }
}
