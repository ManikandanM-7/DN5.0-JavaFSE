package com.cognizant.designpatterns.builder;

/**
 * Builder Pattern (Creational)
 * Scenario: Building a Computer object with many optional components.
 */
public class Computer {

    // Required
    private String cpu;
    private String ram;

    // Optional
    private String storage;
    private String gpu;
    private String os;
    private boolean bluetooth;
    private boolean wifi;

    // Private constructor — only Builder can create
    private Computer(Builder builder) {
        this.cpu       = builder.cpu;
        this.ram       = builder.ram;
        this.storage   = builder.storage;
        this.gpu       = builder.gpu;
        this.os        = builder.os;
        this.bluetooth = builder.bluetooth;
        this.wifi      = builder.wifi;
    }

    @Override
    public String toString() {
        return "Computer {"
                + "\n  CPU       = " + cpu
                + "\n  RAM       = " + ram
                + "\n  Storage   = " + (storage != null ? storage : "None")
                + "\n  GPU       = " + (gpu != null ? gpu : "Integrated")
                + "\n  OS        = " + (os != null ? os : "None")
                + "\n  Bluetooth = " + bluetooth
                + "\n  WiFi      = " + wifi
                + "\n}";
    }

    // Static nested Builder class
    public static class Builder {
        // Required
        private String cpu;
        private String ram;

        // Optional with defaults
        private String storage;
        private String gpu;
        private String os;
        private boolean bluetooth = false;
        private boolean wifi      = false;

        public Builder(String cpu, String ram) {
            this.cpu = cpu;
            this.ram = ram;
        }

        public Builder storage(String storage) {
            this.storage = storage;
            return this;
        }

        public Builder gpu(String gpu) {
            this.gpu = gpu;
            return this;
        }

        public Builder os(String os) {
            this.os = os;
            return this;
        }

        public Builder bluetooth(boolean bluetooth) {
            this.bluetooth = bluetooth;
            return this;
        }

        public Builder wifi(boolean wifi) {
            this.wifi = wifi;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }
    }

    // Main to demonstrate
    public static void main(String[] args) {
        Computer gamingPC = new Computer.Builder("Intel i9", "32GB")
                .storage("2TB SSD")
                .gpu("NVIDIA RTX 4090")
                .os("Windows 11")
                .wifi(true)
                .bluetooth(true)
                .build();

        Computer officePC = new Computer.Builder("Intel i5", "8GB")
                .storage("256GB SSD")
                .os("Ubuntu 22.04")
                .wifi(true)
                .build();

        System.out.println("Gaming PC:");
        System.out.println(gamingPC);
        System.out.println("\nOffice PC:");
        System.out.println(officePC);
    }
}
