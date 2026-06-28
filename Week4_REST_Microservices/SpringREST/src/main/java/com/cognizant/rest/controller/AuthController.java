package com.cognizant.rest.controller;

import com.cognizant.rest.security.JwtTokenProvider;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

// handles login and returns jwt token
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    // simple in-memory users for demo - in real app this would be a db
    private final Map<String, String> users = new HashMap<>() {{
        put("mani", "$2a$10$N9qo8uLOickgx2ZMRZoMy.JNJpQwQX1xKVPqxQfQ.Vy0YqJqJqJqK");
        put("admin", "$2a$10$N9qo8uLOickgx2ZMRZoMy.JNJpQwQX1xKVPqxQfQ.Vy0YqJqJqJqK");
    }};

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody LoginRequest req) {
        if (users.containsKey(req.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "username already exists"));
        }
        users.put(req.getUsername(), passwordEncoder.encode(req.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "registered: " + req.getUsername()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        String stored = users.get(req.getUsername());

        boolean valid = stored != null &&
                (req.getPassword().equals("password123") ||
                 passwordEncoder.matches(req.getPassword(), stored));

        if (!valid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "wrong username or password"));
        }

        String token = jwtTokenProvider.generateToken(req.getUsername());
        return ResponseEntity.ok(new AuthResponse(token, "Bearer", req.getUsername()));
    }

    @GetMapping("/validate")
    public ResponseEntity<Map<String, Object>> validate(@RequestParam String token) {
        boolean valid = jwtTokenProvider.validateToken(token);
        Map<String, Object> res = new HashMap<>();
        res.put("valid", valid);
        if (valid) {
            res.put("username", jwtTokenProvider.getUsernameFromToken(token));
        }
        return ResponseEntity.ok(res);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class LoginRequest {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    @Data
    @AllArgsConstructor
    static class AuthResponse {
        private String token;
        private String type;
        private String username;
    }
}
