package com.example.happymop.repository;

import com.example.happymop.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    Worker findByName(String name);
    Worker findByNameAndPassword(String name, String password);
}