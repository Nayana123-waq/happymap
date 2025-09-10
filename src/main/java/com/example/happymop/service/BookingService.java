package com.example.happymop.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.happymop.model.Booking;
import com.example.happymop.model.ServiceItem;
import com.example.happymop.repository.BookingRepository;
import com.example.happymop.repository.ServiceRepository;

@Service
public class BookingService {
    private final BookingRepository repo;
    private final ServiceRepository serviceRepo;
    private final NotificationService notificationService;
    
    public BookingService(BookingRepository repo, ServiceRepository serviceRepo, NotificationService notificationService){ 
        this.repo = repo; 
        this.serviceRepo = serviceRepo; 
        this.notificationService = notificationService;
    }

    // compute total server-side from serviceIds (CSV of ids) and set amount/serviceName before saving
    public Booking create(Booking b){
        try{
            if(b.getServiceIds() != null && !b.getServiceIds().isBlank()){
                List<Long> ids = Arrays.stream(b.getServiceIds().split(","))
                        .map(String::trim).filter(s->!s.isEmpty()).map(Long::valueOf).collect(Collectors.toList());
                int total = 0;
                String firstTitle = null;
                for(Long id: ids){
                    Optional<ServiceItem> si = serviceRepo.findById(id);
                    if(si.isPresent()){
                        total += si.get().getPrice();
                        if(firstTitle == null) firstTitle = si.get().getTitle();
                    }
                }
                b.setAmount(total);
                if(firstTitle != null) b.setServiceName(firstTitle);
            }
        }catch(Exception ex){
            // fallback: leave amount as provided
        }
        return repo.save(b);
    }

    public List<Booking> all(){ return repo.findAll(); }
    public Optional<Booking> find(Long id){ return repo.findById(id); }
    public void delete(Long id){ repo.deleteById(id); }
    
    public List<Booking> getBookingsByWorker(String workerName) {
        return repo.findByWorkerAssigned(workerName);
    }
    
    public Booking updateBookingStatus(Long bookingId, String status) {
        Optional<Booking> booking = repo.findById(bookingId);
        if (booking.isPresent()) {
            Booking b = booking.get();
            String oldStatus = b.getStatus();
            b.setStatus(status);
            Booking updatedBooking = repo.save(b);
            
            // Send notification for any status change
            if (!status.equals(oldStatus)) {
                try {
                    notificationService.sendStatusNotification(updatedBooking, status);
                    System.out.println("Notification sent for booking #" + bookingId + " status change: " + oldStatus + " -> " + status);
                } catch (Exception e) {
                    // Log error but don't fail the booking update
                    System.err.println("Failed to send status notification: " + e.getMessage());
                }
            }
            
            return updatedBooking;
        }
        return null;
    }
}

