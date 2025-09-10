package com.example.happymop.service;

import com.example.happymop.model.User;
import com.example.happymop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;
    public UserService(UserRepository repo){ this.repo = repo; }
    public User create(User u){ return repo.save(u); }
    public Optional<User> findByEmail(String email){ return repo.findByEmail(email); }
    public List<User> all(){ return repo.findAll(); }
}
