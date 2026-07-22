package com.cognizant.designpatterns.builder;

public class Computer {

    private String cpu;
    private String ram;
    private String storage;
    private String gpu;
    private String os;
    private boolean bluetooth;
    private boolean wifi;

    private Computer(Builder b) {
        this.cpu = b.cpu;
        this.ram = b.ram;
        this.storage = b.storage;
        this.gpu = b.gpu;
        this.os = b.os;
        this.bluetooth = b.bluetooth;
        this.wifi = b.wifi;
    }

    @Override
    public String toString() {
        return "Computer{cpu=" + cpu + ", ram=" + ram + ", storage=" + storage +
               ", gpu=" + gpu + ", os=" + os + ", bt=" + bluetooth + ", wifi=" + wifi + "}";
    }

    public static class Builder {
        private String cpu;
        private String ram;
        private String storage;
        private String gpu;
        private String os;
        private boolean bluetooth = false;
        private boolean wifi = false;

        public Builder(String cpu, String ram) {
            this.cpu = cpu;
            this.ram = ram;
        }

        public Builder storage(String v) { this.storage = v; return this; }
        public Builder gpu(String v) { this.gpu = v; return this; }
        public Builder os(String v) { this.os = v; return this; }
        public Builder bluetooth(boolean v) { this.bluetooth = v; return this; }
        public Builder wifi(boolean v) { this.wifi = v; return this; }
        public Computer build() { return new Computer(this); }
    }

    public static void main(String[] args) {
        Computer gaming = new Computer.Builder("Intel i9", "32GB")
                .storage("2TB SSD").gpu("RTX 4090").os("Windows 11")
                .wifi(true).bluetooth(true).build();

        Computer office = new Computer.Builder("Intel i5", "8GB")
                .storage("256GB SSD").os("Ubuntu 22.04").wifi(true).build();

        System.out.println(gaming);
        System.out.println(office);
    }
}
