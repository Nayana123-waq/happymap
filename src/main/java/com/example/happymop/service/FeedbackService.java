package com.example.happymop.service;

import com.example.happymop.model.Feedback;
import com.example.happymop.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {
    private final FeedbackRepository repo;
    public FeedbackService(FeedbackRepository repo){ this.repo = repo; }
    public Feedback create(Feedback f){ return repo.save(f); }
    public List<Feedback> all(){ return repo.findAll(); }
    public void delete(Long id){ repo.deleteById(id); }
}

