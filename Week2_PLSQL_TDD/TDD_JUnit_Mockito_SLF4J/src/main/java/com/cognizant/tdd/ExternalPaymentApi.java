package com.cognizant.tdd;

/** External payment gateway interface — will be mocked in Mockito tests */
public interface ExternalPaymentApi {
    String sendPayment(String customerId, double amount, String currency);
    boolean validateCard(String cardNumber);
    void notifyCustomer(String customerId, String message);
}
