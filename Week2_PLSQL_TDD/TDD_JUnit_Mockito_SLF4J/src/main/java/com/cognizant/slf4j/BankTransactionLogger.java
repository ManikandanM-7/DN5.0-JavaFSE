package com.cognizant.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// slf4j logging exercise - different log levels
public class BankTransactionLogger {

    private static final Logger log = LoggerFactory.getLogger(BankTransactionLogger.class);

    public void showLogLevels() {
        log.trace("trace level - very detailed");
        log.debug("debug - variable values etc");
        log.info("info - normal app events");
        log.warn("warn - something might be wrong");
        log.error("error - something went wrong");
    }

    public String processTransaction(long customerId, double amount, String type) {
        log.info("processing {} for customer {} amount {}", type, customerId, amount);

        if (amount <= 0) {
            log.error("invalid amount: {}", amount);
            throw new IllegalArgumentException("amount must be positive");
        }

        if (amount > 100000) {
            log.warn("large transaction: {} by customer {}", amount, customerId);
        }

        String txnId = "TXN-" + System.currentTimeMillis();
        log.debug("generated txnId: {}", txnId);
        log.info("transaction {} completed", txnId);
        return txnId;
    }

    public void dbError(long customerId) {
        try {
            throw new RuntimeException("db connection refused");
        } catch (RuntimeException e) {
            // passing exception as last arg prints stack trace
            log.error("db error for customer {}: {}", customerId, e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        BankTransactionLogger logger = new BankTransactionLogger();
        logger.showLogLevels();
        System.out.println();
        logger.processTransaction(1L, 5000.0, "CREDIT");
        System.out.println();
        logger.processTransaction(2L, 200000.0, "DEBIT");
        System.out.println();
        logger.dbError(3L);
    }
}
