package com.cognizant.designpatterns.adapter;

// adapter pattern
// our system expects PaymentProcessor interface
// but the sdk we got uses different method names
// adapter makes them compatible without changing either

interface PaymentProcessor {
    boolean processPayment(double amount, String currency);
    String getTransactionId();
}

// third party sdk - cant modify this
class PayPalSDK {
    public void sendPayment(String amount, String currencyCode) {
        System.out.println("PayPal SDK: sending " + amount + " " + currencyCode);
    }

    public boolean isPaymentSuccessful() {
        return true;
    }

    public String getPayPalTransactionRef() {
        return "PAYPAL-" + System.currentTimeMillis();
    }
}

// adapter wraps the sdk and implements our interface
public class PayPalAdapter implements PaymentProcessor {

    private PayPalSDK payPalSDK;
    private String lastTxnId;

    public PayPalAdapter() {
        this.payPalSDK = new PayPalSDK();
    }

    @Override
    public boolean processPayment(double amount, String currency) {
        payPalSDK.sendPayment(String.format("%.2f", amount), currency);
        boolean success = payPalSDK.isPaymentSuccessful();
        if (success) {
            lastTxnId = payPalSDK.getPayPalTransactionRef();
        }
        return success;
    }

    @Override
    public String getTransactionId() {
        return lastTxnId;
    }

    public static void main(String[] args) {
        PaymentProcessor processor = new PayPalAdapter();
        boolean result = processor.processPayment(1500.0, "USD");
        System.out.println("success: " + result);
        System.out.println("txnId: " + processor.getTransactionId());
    }
}
