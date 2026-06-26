package com.cognizant.tdd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BankService — main service class used across JUnit + Mockito exercises.
 * Depends on ExternalPaymentApi and CustomerRepository (to be mocked in tests).
 */
public class BankService {

    private static final Logger log = LoggerFactory.getLogger(BankService.class);

    private final ExternalPaymentApi paymentApi;
    private final CustomerRepository customerRepo;

    public BankService(ExternalPaymentApi paymentApi, CustomerRepository customerRepo) {
        this.paymentApi   = paymentApi;
        this.customerRepo = customerRepo;
    }

    public double getBalance(long customerId) {
        log.info("Fetching balance for customer: {}", customerId);
        Customer customer = customerRepo.findById(customerId);
        if (customer == null) {
            log.warn("Customer {} not found", customerId);
            throw new IllegalArgumentException("Customer not found: " + customerId);
        }
        return customer.getBalance();
    }

    public String processPayment(long customerId, double amount, String currency) {
        log.info("Processing payment: customerId={}, amount={} {}", customerId, amount, currency);

        if (amount <= 0) {
            log.error("Invalid payment amount: {}", amount);
            throw new IllegalArgumentException("Amount must be positive");
        }

        String result = paymentApi.sendPayment(String.valueOf(customerId), amount, currency);
        log.debug("Payment API response: {}", result);
        return result;
    }

    public boolean transferFunds(long fromId, long toId, double amount) {
        log.info("Transfer: from={} to={} amount={}", fromId, toId, amount);
        double senderBalance = getBalance(fromId);

        if (senderBalance < amount) {
            log.warn("Insufficient funds for customer {}: has {}, needs {}", fromId, senderBalance, amount);
            return false;
        }

        customerRepo.updateBalance(fromId, senderBalance - amount);
        Customer receiver = customerRepo.findById(toId);
        customerRepo.updateBalance(toId, receiver.getBalance() + amount);

        log.info("Transfer completed successfully");
        return true;
    }

    public int add(int a, int b) {
        return a + b;
    }

    public boolean isEven(int number) {
        return number % 2 == 0;
    }
}
