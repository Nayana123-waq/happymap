package com.example.happymop.service;

import com.example.happymop.model.WorkerApplication;
import com.example.happymop.repository.WorkerApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerApplicationService {
    private final WorkerApplicationRepository repo;
    public WorkerApplicationService(WorkerApplicationRepository repo){ this.repo = repo; }
    public WorkerApplication create(WorkerApplication a){ return repo.save(a); }
    public List<WorkerApplication> all(){ return repo.findAll(); }
    public Optional<WorkerApplication> find(Long id){ return repo.findById(id); }
    public void delete(Long id){ repo.deleteById(id); }
}
