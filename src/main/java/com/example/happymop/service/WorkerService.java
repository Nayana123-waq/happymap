package com.example.happymop.service;

import com.example.happymop.model.Worker;
import com.example.happymop.repository.WorkerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    private final WorkerRepository repo;
    public WorkerService(WorkerRepository repo){ this.repo = repo; }
    public Worker create(Worker w){ return repo.save(w); }
    public List<Worker> all(){ return repo.findAll(); }
    public Optional<Worker> find(Long id){ return repo.findById(id); }
    public void delete(Long id){ repo.deleteById(id); }
}
