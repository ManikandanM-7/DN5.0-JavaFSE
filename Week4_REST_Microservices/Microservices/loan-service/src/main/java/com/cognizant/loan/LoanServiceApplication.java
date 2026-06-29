package com.cognizant.loan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 
 * Runs on port 8082, registers with Eureka as "loan-service"
 */
@SpringBootApplication
@EnableDiscoveryClient
public class LoanServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoanServiceApplication.class, args);
        System.out.println("loan-service up on 8082");
    }
}
