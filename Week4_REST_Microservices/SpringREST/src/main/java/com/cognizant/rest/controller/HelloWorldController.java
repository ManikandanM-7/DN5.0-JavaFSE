package com.cognizant.rest.controller;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HelloWorldController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!!";
    }

    @GetMapping("/hello/json")
    public Map<String, Object> helloJson() {
        return Map.of(
            "message", "Hello World!!",
            "time", LocalDateTime.now().toString()
        );
    }

    @GetMapping("/hello/{name}")
    public String helloName(@PathVariable String name) {
        return "Hello " + name + "!";
    }
}
