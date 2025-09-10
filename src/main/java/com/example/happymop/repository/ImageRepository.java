package com.example.happymop.repository;

import com.example.happymop.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByCategory(String category);
    List<Image> findByNameContainingIgnoreCase(String name);
    List<Image> findByCategoryOrderByUploadDateDesc(String category);
}
