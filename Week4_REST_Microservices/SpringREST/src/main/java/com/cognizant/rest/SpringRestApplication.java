package com.cognizant.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRestApplication.class, args);
        System.out.println("spring rest app started");
        System.out.println("swagger: http://localhost:8080/swagger-ui.html");
    }
}
