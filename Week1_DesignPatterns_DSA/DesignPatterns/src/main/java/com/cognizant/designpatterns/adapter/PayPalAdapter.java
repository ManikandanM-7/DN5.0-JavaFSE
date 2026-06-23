package com.cognizant.designpatterns.adapter;

/**
 * Adapter Pattern (Structural)
 * Scenario: Integrating a third-party payment gateway (PayPal) into an existing
 * payment system that expects a different interface.
 */

// Existing interface that our system uses
interface PaymentProcessor {
    boolean processPayment(double amount, String currency);
    String getTransactionId();
}

// Third-party PayPal SDK (cannot be modified — external library)
class PayPalSDK {
    public void sendPayment(String amount, String currencyCode) {
        System.out.println("PayPal SDK: Sending " + amount + " " + currencyCode);
    }

    public boolean isPaymentSuccessful() {
        return true; // Simulated success
    }

    public String getPayPalTransactionRef() {
        return "PAYPAL-TXN-" + System.currentTimeMillis();
    }
}

// Adapter — wraps PayPalSDK to match PaymentProcessor interface
public class PayPalAdapter implements PaymentProcessor {

    private PayPalSDK payPalSDK;
    private String lastTransactionId;

    public PayPalAdapter() {
        this.payPalSDK = new PayPalSDK();
    }

    @Override
    public boolean processPayment(double amount, String currency) {
        // Adapt: convert double to String format expected by PayPal SDK
        payPalSDK.sendPayment(String.format("%.2f", amount), currency);
        boolean success = payPalSDK.isPaymentSuccessful();
        if (success) {
            lastTransactionId = payPalSDK.getPayPalTransactionRef();
        }
        return success;
    }

    @Override
    public String getTransactionId() {
        return lastTransactionId;
    }

    // Demo main
    public static void main(String[] args) {
        PaymentProcessor processor = new PayPalAdapter();
        boolean result = processor.processPayment(1500.00, "USD");
        System.out.println("Payment successful: " + result);
        System.out.println("Transaction ID: " + processor.getTransactionId());
    }
}
