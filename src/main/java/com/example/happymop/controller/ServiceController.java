package com.example.happymop.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.happymop.model.ServiceItem;
import com.example.happymop.repository.ServiceRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/services")
public class ServiceController {
    private final ServiceRepository repo;
    public ServiceController(ServiceRepository repo){ this.repo = repo; }

    @GetMapping
    public List<ServiceItem> getAllServices(){ 
        return repo.findAll(); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceItem> getServiceById(@PathVariable Long id){
        Optional<ServiceItem> service = repo.findById(id);
        return service.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ServiceItem> getServiceByCode(@PathVariable String code){
        Optional<ServiceItem> service = repo.findByCode(code);
        return service.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ServiceItem> createService(@RequestBody ServiceItem service){
        ServiceItem created = repo.save(service);
        return ResponseEntity.created(URI.create("/api/services/"+created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceItem> updateService(@PathVariable Long id, @RequestBody ServiceItem service){
        if(!repo.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        service.setId(id);
        ServiceItem updated = repo.save(service);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id){
        if(!repo.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
