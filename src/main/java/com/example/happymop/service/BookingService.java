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
    public BookingService(BookingRepository repo, ServiceRepository serviceRepo){ this.repo = repo; this.serviceRepo = serviceRepo; }

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
}

