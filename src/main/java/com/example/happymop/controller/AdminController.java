package com.example.happymop.controller;

import com.example.happymop.model.WorkerApplication;
import com.example.happymop.model.Worker;
import com.example.happymop.service.WorkerApplicationService;
import com.example.happymop.service.WorkerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/admin")
public class AdminController {
    private final WorkerApplicationService appSvc;
    private final WorkerService workerSvc;
    public AdminController(WorkerApplicationService appSvc, WorkerService workerSvc){ this.appSvc = appSvc; this.workerSvc = workerSvc; }

    @GetMapping("/applications")
    public ResponseEntity<List<WorkerApplication>> apps(){
        try {
            System.out.println("Admin: Fetching worker applications");
            List<WorkerApplication> applications = appSvc.all();
            System.out.println("Admin: Found " + applications.size() + " applications");
            return ResponseEntity.ok(applications);
        } catch (Exception e) {
            System.err.println("Admin: Failed to fetch applications: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/applications")
    public ResponseEntity<WorkerApplication> createApp(@RequestBody WorkerApplication a){
        try {
            System.out.println("Admin: Creating worker application for: " + a.getName());
            WorkerApplication created = appSvc.create(a);
            System.out.println("Admin: Application created with ID: " + created.getId());
            return ResponseEntity.created(URI.create("/api/admin/applications/"+created.getId())).body(created);
        } catch (Exception e) {
            System.err.println("Admin: Failed to create application: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/applications/{id}/approve")
    public ResponseEntity<Worker> approve(@PathVariable Long id){
        WorkerApplication app = appSvc.find(id).orElseThrow();
        // create worker
        Worker w = new Worker();
        w.setName(app.getName()); w.setPhone(app.getPhone()); w.setServices(app.getServices()); w.setArea(app.getArea());
        Worker created = workerSvc.create(w);
        app.setStatus("approved"); appSvc.create(app);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/applications/{id}/reject")
    public ResponseEntity<Void> reject(@PathVariable Long id){ appSvc.delete(id); return ResponseEntity.noContent().build(); }
}