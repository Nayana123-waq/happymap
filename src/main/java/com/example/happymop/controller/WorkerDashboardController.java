package com.example.happymop.controller;

import com.example.happymop.model.Booking;
import com.example.happymop.service.BookingService;
import com.example.happymop.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/worker")
@CrossOrigin(origins = "*")
public class WorkerDashboardController {
    
    private static final Logger logger = LoggerFactory.getLogger(WorkerDashboardController.class);
    
    private final BookingService bookingService;
    private final NotificationService notificationService;
    
    public WorkerDashboardController(BookingService bookingService, NotificationService notificationService) {
        this.bookingService = bookingService;
        this.notificationService = notificationService;
    }
    
    @GetMapping("/bookings/{workerName}")
    public ResponseEntity<List<Booking>> getWorkerBookings(@PathVariable String workerName) {
        try {
            logger.info("Fetching bookings for worker: {}", workerName);
            List<Booking> bookings = bookingService.getBookingsByWorker(workerName);
            logger.info("Found {} bookings for worker: {}", bookings.size(), workerName);
            return ResponseEntity.ok(bookings);
        } catch (Exception e) {
            logger.error("Error fetching bookings for worker {}: {}", workerName, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // Debug endpoint to test data retrieval
    @GetMapping("/debug/all-bookings")
    public ResponseEntity<List<Booking>> getAllBookingsDebug() {
        try {
            logger.info("Debug: Fetching all bookings");
            List<Booking> allBookings = bookingService.all();
            logger.info("Debug: Found {} total bookings", allBookings.size());
            return ResponseEntity.ok(allBookings);
        } catch (Exception e) {
            logger.error("Debug: Error fetching all bookings: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // Debug endpoint to test specific worker assignment
    @GetMapping("/debug/worker/{workerName}")
    public ResponseEntity<String> debugWorkerBookings(@PathVariable String workerName) {
        try {
            logger.info("Debug: Testing worker assignment for: {}", workerName);
            List<Booking> bookings = bookingService.getBookingsByWorker(workerName);
            List<Booking> allBookings = bookingService.all();
            
            StringBuilder debug = new StringBuilder();
            debug.append("Worker: ").append(workerName).append("\n");
            debug.append("Bookings found: ").append(bookings.size()).append("\n");
            debug.append("Total bookings: ").append(allBookings.size()).append("\n\n");
            
            debug.append("All bookings with worker assignments:\n");
            for (Booking booking : allBookings) {
                debug.append("ID: ").append(booking.getId())
                     .append(", Worker: '").append(booking.getWorkerAssigned())
                     .append("', Status: ").append(booking.getStatus())
                     .append(", Service: ").append(booking.getServiceName()).append("\n");
            }
            
            return ResponseEntity.ok(debug.toString());
        } catch (Exception e) {
            logger.error("Debug: Error in worker debug: {}", e.getMessage(), e);
            return ResponseEntity.ok("Error: " + e.getMessage());
        }
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
