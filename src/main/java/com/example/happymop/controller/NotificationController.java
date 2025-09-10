package com.example.happymop.controller;

import com.example.happymop.model.Notification;
import com.example.happymop.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {
    
    private final NotificationService notificationService;
    
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<Notification>> getNotificationsByBooking(@PathVariable Long bookingId) {
        List<Notification> notifications = notificationService.getNotificationsByBooking(bookingId);
        return ResponseEntity.ok(notifications);
    }
    
    @GetMapping("/customer/{customerPhone}")
    public ResponseEntity<List<Notification>> getNotificationsByCustomer(@PathVariable String customerPhone) {
        List<Notification> notifications = notificationService.getNotificationsByCustomer(customerPhone);
        return ResponseEntity.ok(notifications);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Notification>> getAllNotifications() {
        List<Notification> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }
}
