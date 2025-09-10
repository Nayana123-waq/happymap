package com.example.happymop.controller;

import com.example.happymop.model.Worker;
import com.example.happymop.service.WorkerService;
import com.example.happymop.repository.FeedbackRepository;
import com.example.happymop.model.Feedback;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/workers")
public class WorkerController {
    private final WorkerService svc;
    private final FeedbackRepository feedbackRepo;
    public WorkerController(WorkerService svc, FeedbackRepository feedbackRepo){ this.svc = svc; this.feedbackRepo = feedbackRepo; }

    @GetMapping
    public List<Worker> all(){
        List<Worker> workers = svc.all();
        // compute avg rating for each worker
        for(Worker w: workers){
            List<Feedback> f = feedbackRepo.findByWorkerId(w.getId());
            if(f == null || f.isEmpty()) { w.setAvgRating(null); }
            else {
                double avg = f.stream().mapToInt(Feedback::getRating).average().orElse(0.0);
                w.setAvgRating(Math.round(avg*10.0)/10.0); // one decimal
                w.setReviewCount(f.size());
            }
        }
        return workers;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Worker> get(@PathVariable Long id){
        return svc.find(id).map(w -> ResponseEntity.ok(w)).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Worker> create(@RequestBody Worker w){
        Worker created = svc.create(w);
        return ResponseEntity.created(URI.create("/api/workers/"+created.getId())).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){ svc.delete(id); return ResponseEntity.noContent().build(); }
}
