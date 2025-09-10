package com.example.happymop.controller;

import com.example.happymop.model.User;
import com.example.happymop.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userSvc;
    public AuthController(UserService userSvc){ this.userSvc = userSvc; }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> payload){
        String email = payload.get("email");
        String password = payload.get("password");
        if(email == null || password == null) return ResponseEntity.badRequest().body(Map.of("error","email and password required"));
        return userSvc.findByEmail(email).map(u -> {
            if(password.equals(u.getPassword())){
                return ResponseEntity.ok(Map.of("id", u.getId(), "name", u.getName(), "email", u.getEmail()));
            } else {
                return ResponseEntity.status(401).body(Map.of("error","invalid credentials"));
            }
        }).orElse(ResponseEntity.status(404).body(Map.of("error","user not found")));
    }
}
