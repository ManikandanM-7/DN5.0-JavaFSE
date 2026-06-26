package com.cognizant.tdd;

public interface CustomerRepository {
    Customer findById(long id);
    void updateBalance(long id, double newBalance);
    void save(Customer customer);
    void delete(long id);
}
