package com.example.happymop.service;

import com.example.happymop.model.Booking;
import com.example.happymop.model.Notification;
import com.example.happymop.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    
    private final NotificationRepository notificationRepository;
    
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    
    public Notification sendStatusNotification(Booking booking, String status) {
        String message = createStatusMessage(booking, status);
        
        Notification notification = new Notification(
            booking.getId(),
            booking.getUserName(),
            booking.getUserPhone(),
            message
        );
        
        notification.setType(status);
        notification.setStatus("sent");
        
        return notificationRepository.save(notification);
    }
    
    public Notification sendCompletionNotification(Booking booking) {
        return sendStatusNotification(booking, "completed");
    }
    
    private String createStatusMessage(Booking booking, String status) {
        String workerName = booking.getWorkerAssigned();
        String serviceName = booking.getServiceName();
        String customerName = booking.getUserName();
        
        switch (status.toLowerCase()) {
            case "reached":
                return String.format(
                    "Dear %s, your worker %s has reached your location for %s service. " +
                    "Booking #%d is about to begin. Please be available.",
                    customerName, workerName, serviceName, booking.getId()
                );
            case "in-progress":
                return String.format(
                    "Dear %s, your %s service has started! Worker %s is now working on your booking #%d. " +
                    "Estimated completion time will be communicated shortly.",
                    customerName, serviceName, workerName, booking.getId()
                );
            case "completed":
                return String.format(
                    "Dear %s, your service booking #%d has been completed successfully! " +
                    "Service: %s, Amount: ₹%d, Worker: %s. Thank you for choosing HappyMop!",
                    customerName, booking.getId(), serviceName, booking.getAmount(), workerName
                );
            default:
                return String.format(
                    "Dear %s, your booking #%d status has been updated to: %s",
                    customerName, booking.getId(), status
                );
        }
    }
    
    public List<Notification> getNotificationsByBooking(Long bookingId) {
        return notificationRepository.findByBookingId(bookingId);
    }
    
    public List<Notification> getNotificationsByCustomer(String customerPhone) {
        return notificationRepository.findByCustomerPhone(customerPhone);
    }
    
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
}
