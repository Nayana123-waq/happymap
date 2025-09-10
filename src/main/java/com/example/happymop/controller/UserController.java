package com.example.happymop.controller;

import com.example.happymop.model.User;
import com.example.happymop.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
public class UserController {
    private final UserService svc;
    public UserController(UserService svc){ this.svc = svc; }

    @GetMapping
    public List<User> all(){ return svc.all(); }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User u){
        try {
            System.out.println("Registration request received for email: " + u.getEmail());
            // Check if user already exists
            if (svc.findByEmail(u.getEmail()).isPresent()) {
                System.out.println("User already exists with email: " + u.getEmail());
                return ResponseEntity.badRequest().body("{\"error\":\"User already exists with this email\"}");
            }
            User created = svc.create(u);
            System.out.println("User created successfully with ID: " + created.getId());
            // Don't return password in response
            created.setPassword(null);
            return ResponseEntity.created(URI.create("/api/users/"+created.getId())).body(created);
        } catch (Exception e) {
            System.err.println("Registration failed: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("{\"error\":\"Registration failed: " + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try {
            User user = svc.findByEmail(loginRequest.getEmail()).orElse(null);
            if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
                // Don't return password in response
                user.setPassword(null);
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.badRequest().body("{\"error\":\"Invalid email or password\"}");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\":\"Login failed\"}");
        }
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User u){
        User created = svc.create(u);
        return ResponseEntity.created(URI.create("/api/users/"+created.getId())).body(created);
    }

    // Inner class for login request
    public static class LoginRequest {
        private String email;
        private String password;
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}

