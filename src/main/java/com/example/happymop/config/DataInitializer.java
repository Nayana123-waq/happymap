package com.example.happymop.config;

import com.example.happymop.entity.Image;
import com.example.happymop.model.ServiceItem;
import com.example.happymop.model.User;
import com.example.happymop.repository.ImageRepository;
import com.example.happymop.repository.ServiceRepository;
import com.example.happymop.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    
    public DataInitializer(ServiceRepository serviceRepository, UserRepository userRepository, ImageRepository imageRepository) {
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Initialize services if none exist
        if (serviceRepository.count() == 0) {
            initializeServices();
        }
        
        // Initialize sample users if none exist
        if (userRepository.count() == 0) {
            initializeUsers();
        }
        
        // Initialize sample images if none exist
        if (imageRepository.count() == 0) {
            initializeImages();
        }
    }
    
    private void initializeServices() {
        ServiceItem[] services = {
            createService("home_cleaning", "Home Cleaning", 1200, "Complete home cleaning including dusting, mopping, and organizing"),
            createService("deep_cleaning", "Deep Cleaning", 2500, "Thorough deep cleaning including kitchen, bathrooms, and all rooms"),
            createService("office_cleaning", "Office Cleaning", 1800, "Professional office space cleaning and sanitization"),
            createService("carpet_cleaning", "Carpet Cleaning", 800, "Professional carpet and upholstery cleaning"),
            createService("window_cleaning", "Window Cleaning", 600, "Interior and exterior window cleaning"),
            createService("kitchen_clean", "Kitchen Deep Clean", 1500, "Complete kitchen cleaning including appliances and cabinets"),
            createService("bathroom_clean", "Bathroom Deep Clean", 1000, "Thorough bathroom cleaning and sanitization"),
            createService("construction_cleanup", "Post-Construction Cleanup", 3500, "Complete cleanup after construction or renovation work"),
            createService("move_cleaning", "Move-in/Move-out Cleaning", 2200, "Complete cleaning for moving in or out of property"),
            createService("laundry", "Laundry Service", 500, "Washing, drying, and folding of clothes")
        };
        
        for (ServiceItem service : services) {
            serviceRepository.save(service);
        }
        
        System.out.println("Initialized " + services.length + " services in the database");
    }
    
    private void initializeUsers() {
        User[] users = {
            createUser("John Doe", "john@example.com", "9876543210", "password123", "123 Main Street, Mumbai"),
            createUser("Jane Smith", "jane@example.com", "9876543211", "password123", "456 Park Avenue, Delhi"),
            createUser("Raj Patel", "raj@example.com", "9876543212", "password123", "789 Garden Road, Bangalore"),
            createUser("Priya Sharma", "priya@example.com", "9876543213", "password123", "321 Lake View, Chennai"),
            createUser("Test User", "test@test.com", "1234567890", "test123", "Test Address")
        };
        
        for (User user : users) {
            userRepository.save(user);
        }
        
        System.out.println("Initialized " + users.length + " sample users in the database");
    }
    
    private ServiceItem createService(String code, String title, int price, String description) {
        ServiceItem service = new ServiceItem();
        service.setCode(code);
        service.setTitle(title);
        service.setPrice(price);
        service.setDescription(description);
        return service;
    }
    
    private User createUser(String name, String email, String phone, String password, String address) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        user.setAddress(address);
        return user;
    }
    
    private void initializeImages() {
        // Sample images as base64 encoded data (small placeholder images)
        String[] sampleImages = {
            createSampleImageData("cleaning-service-1.jpg", "service", "Professional home cleaning service"),
            createSampleImageData("cleaning-service-2.jpg", "service", "Deep cleaning specialists"),
            createSampleImageData("office-cleaning.jpg", "service", "Office cleaning professionals"),
            createSampleImageData("team-member-1.jpg", "team", "Our professional cleaning expert"),
            createSampleImageData("team-member-2.jpg", "team", "Experienced cleaning specialist"),
            createSampleImageData("gallery-1.jpg", "gallery", "Before and after cleaning results"),
            createSampleImageData("gallery-2.jpg", "gallery", "Quality cleaning work showcase"),
            createSampleImageData("testimonial-bg.jpg", "background", "Customer testimonial background")
        };
        
        for (String imageData : sampleImages) {
            String[] parts = imageData.split("\\|");
            if (parts.length == 4) {
                Image image = new Image();
                image.setName(parts[0]);
                image.setContentType("image/jpeg");
                image.setData(Base64.getDecoder().decode(parts[1]));
                image.setCategory(parts[2]);
                image.setDescription(parts[3]);
                imageRepository.save(image);
            }
        }
        
        System.out.println("Initialized " + sampleImages.length + " sample images in the database");
    }
    
    private String createSampleImageData(String name, String category, String description) {
        // Create a minimal JPEG header for a 1x1 pixel image (placeholder)
        String base64Data = "/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQH/2wBDAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQH/wAARCAABAAEDASIAAhEBAxEB/8QAFQABAQAAAAAAAAAAAAAAAAAAAAv/xAAUEAEAAAAAAAAAAAAAAAAAAAAA/8QAFQEBAQAAAAAAAAAAAAAAAAAAAAX/xAAUEQEAAAAAAAAAAAAAAAAAAAAA/9oADAMBAAIRAxEAPwA/wA==";
        return name + "|" + base64Data + "|" + category + "|" + description;
    }
}
