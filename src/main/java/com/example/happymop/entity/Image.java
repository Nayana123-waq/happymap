package com.example.happymop.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String contentType;
    
    @Lob
    @Column(nullable = false, columnDefinition = "LONGBLOB")
    private byte[] data;
    
    @Column(nullable = false)
    private Long size;
    
    @Column(nullable = false)
    private LocalDateTime uploadDate;
    
    private String description;
    
    @Column(nullable = false)
    private String category; // service, team, gallery, etc.
    
    // Constructors
    public Image() {
        this.uploadDate = LocalDateTime.now();
    }
    
    public Image(String name, String contentType, byte[] data, String category) {
        this.name = name;
        this.contentType = contentType;
        this.data = data;
        this.size = (long) data.length;
        this.category = category;
        this.uploadDate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getContentType() {
        return contentType;
    }
    
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
    public byte[] getData() {
        return data;
    }
    
    public void setData(byte[] data) {
        this.data = data;
        this.size = (long) data.length;
    }
    
    public Long getSize() {
        return size;
    }
    
    public void setSize(Long size) {
        this.size = size;
    }
    
    public LocalDateTime getUploadDate() {
        return uploadDate;
    }
    
    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
}
