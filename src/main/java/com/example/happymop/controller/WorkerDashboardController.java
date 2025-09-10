package com.example.happymop.controller;

import com.example.happymop.model.Booking;
import com.example.happymop.service.BookingService;
import com.example.happymop.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/worker")
@CrossOrigin(origins = "*")
public class WorkerDashboardController {
    
    private final BookingService bookingService;
    private final NotificationService notificationService;
    
    public WorkerDashboardController(BookingService bookingService, NotificationService notificationService) {
        this.bookingService = bookingService;
        this.notificationService = notificationService;
    }
    
    @GetMapping("/bookings/{workerName}")
    public ResponseEntity<List<Booking>> getWorkerBookings(@PathVariable String workerName) {
        List<Booking> bookings = bookingService.getBookingsByWorker(workerName);
        return ResponseEntity.ok(bookings);
    }
    
    @PutMapping("/bookings/{bookingId}/status")
    public ResponseEntity<Booking> updateBookingStatus(
            @PathVariable Long bookingId, 
            @RequestParam String status) {
        
        Booking updatedBooking = bookingService.updateBookingStatus(bookingId, status);
        
        if (updatedBooking != null) {
            // Send notification to customer about status update
            notificationService.sendStatusNotification(updatedBooking, status);
            return ResponseEntity.ok(updatedBooking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/bookings/{bookingId}/reached")
    public ResponseEntity<String> markAsReached(@PathVariable Long bookingId) {
        Booking booking = bookingService.updateBookingStatus(bookingId, "reached");
        if (booking != null) {
            notificationService.sendStatusNotification(booking, "reached");
            return ResponseEntity.ok("Booking marked as reached and customer notified");
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/bookings/{bookingId}/start")
    public ResponseEntity<String> startWork(@PathVariable Long bookingId) {
        Booking booking = bookingService.updateBookingStatus(bookingId, "in-progress");
        if (booking != null) {
            notificationService.sendStatusNotification(booking, "in-progress");
            return ResponseEntity.ok("Work started and customer notified");
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/bookings/{bookingId}/complete")
    public ResponseEntity<String> completeWork(@PathVariable Long bookingId) {
        Booking booking = bookingService.updateBookingStatus(bookingId, "completed");
        if (booking != null) {
            notificationService.sendCompletionNotification(booking);
            return ResponseEntity.ok("Work completed and customer notified");
        }
        return ResponseEntity.notFound().build();
    }
}
