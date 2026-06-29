package com.cognizant.account.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 
 *
 * Endpoint: GET /accounts/{number}
 * Sample Response (dummy, no backend connectivity):
 *   { "number": "00987987973432", "type": "savings", "balance": 234343 }
 */
@RestController
public class AccountController {

    @GetMapping("/accounts/{number}")
    public Map<String, Object> getAccountDetails(@PathVariable String number) {
        Map<String, Object> response = new HashMap<>();
        response.put("number",  number);
        response.put("type",    "savings");
        response.put("balance", 234343);
        response.put("holder",  "Mani");
        response.put("service", "account-service [port 8081]");
        return response;
    }

    @GetMapping("/accounts/health")
    public String health() {
        return "account-service is UP";
    }
}
