package com.cognizant.tdd;

public class Customer {
    private long   id;
    private String name;
    private double balance;
    private String email;

    public Customer(long id, String name, double balance, String email) {
        this.id      = id;
        this.name    = name;
        this.balance = balance;
        this.email   = email;
    }

    public long   getId()      { return id; }
    public String getName()    { return name; }
    public double getBalance() { return balance; }
    public String getEmail()   { return email; }

    public void setBalance(double balance) { this.balance = balance; }

    @Override
    public String toString() {
        return "Customer{id=" + id + ", name='" + name + "', balance=" + balance + "}";
    }
}
