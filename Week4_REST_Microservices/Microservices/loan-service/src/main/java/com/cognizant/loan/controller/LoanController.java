package com.cognizant.loan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 
 *
 * Endpoint: GET /loans/{number}
 * Sample Response (dummy, no backend connectivity):
 *   { "number": "H00987987972342", "type": "car", "loan": 400000, "emi": 3258, "tenure": 18 }
 */
@RestController
public class LoanController {

    @GetMapping("/loans/{number}")
    public Map<String, Object> getLoanDetails(@PathVariable String number) {
        Map<String, Object> response = new HashMap<>();
        response.put("number",  number);
        response.put("type",    "car");
        response.put("loan",    400000);
        response.put("emi",     3258);
        response.put("tenure",  18);
        response.put("service", "loan-service [port 8082]");
        return response;
    }

    @GetMapping("/loans/health")
    public String health() {
        return "loan-service is UP";
    }
}
