package com.example.happymop.repository;

import com.example.happymop.model.ServiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<ServiceItem, Long> {
    Optional<ServiceItem> findByCode(String code);
    Optional<ServiceItem> findByTitle(String title);
}
