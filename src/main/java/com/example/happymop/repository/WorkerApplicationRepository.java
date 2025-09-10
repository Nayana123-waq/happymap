package com.example.happymop.repository;

import com.example.happymop.model.WorkerApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerApplicationRepository extends JpaRepository<WorkerApplication, Long> {
}