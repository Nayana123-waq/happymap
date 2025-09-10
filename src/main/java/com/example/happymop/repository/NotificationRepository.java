package com.example.happymop.repository;

import com.example.happymop.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByBookingId(Long bookingId);
    List<Notification> findByCustomerPhone(String customerPhone);
}
