package com.example.happymop.service;

import com.example.happymop.entity.Image;
import com.example.happymop.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    
    @Autowired
    private ImageRepository imageRepository;
    
    public Image saveImage(MultipartFile file, String category, String description) throws IOException {
        Image image = new Image();
        image.setName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setData(file.getBytes());
        image.setCategory(category);
        image.setDescription(description);
        
        return imageRepository.save(image);
    }
    
    public Image saveImage(String name, String contentType, byte[] data, String category, String description) {
        Image image = new Image(name, contentType, data, category);
        image.setDescription(description);
        return imageRepository.save(image);
    }
    
    public Optional<Image> getImage(Long id) {
        return imageRepository.findById(id);
    }
    
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }
    
    public List<Image> getImagesByCategory(String category) {
        return imageRepository.findByCategoryOrderByUploadDateDesc(category);
    }
    
    public List<Image> searchImages(String name) {
        return imageRepository.findByNameContainingIgnoreCase(name);
    }
    
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
}
