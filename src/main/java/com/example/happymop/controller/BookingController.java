package com.example.happymop.controller;

import com.example.happymop.model.Booking;
import com.example.happymop.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService svc;
    public BookingController(BookingService svc){ this.svc = svc; }

    @GetMapping
    public List<Booking> all(){ return svc.all(); }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> get(@PathVariable Long id){
        return svc.find(id).map(b -> ResponseEntity.ok(b)).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Booking> create(@RequestBody Booking b){
        Booking created = svc.create(b);
        return ResponseEntity.created(URI.create("/api/bookings/"+created.getId())).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){ svc.delete(id); return ResponseEntity.noContent().build(); }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> update(@PathVariable Long id, @RequestBody Booking updated){
        return svc.find(id).map(existing -> {
            // update fields (simple merge)
            existing.setServiceId(updated.getServiceId());
            existing.setServiceName(updated.getServiceName());
            existing.setLocation(updated.getLocation());
            existing.setDate(updated.getDate());
            existing.setTime(updated.getTime());
            existing.setUserName(updated.getUserName());
            existing.setUserPhone(updated.getUserPhone());
            existing.setWorkerAssigned(updated.getWorkerAssigned());
            existing.setStatus(updated.getStatus());
            existing.setPaidWith(updated.getPaidWith());
            Booking saved = svc.create(existing);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }
}
