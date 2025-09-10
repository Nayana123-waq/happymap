package com.example.happymop.controller;

import com.example.happymop.model.Feedback;
import com.example.happymop.service.FeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/feedbacks")
public class FeedbackController {
    private final FeedbackService svc;
    public FeedbackController(FeedbackService svc){ this.svc = svc; }

    @GetMapping
    public List<Feedback> all(){ return svc.all(); }

    @PostMapping
    public ResponseEntity<Feedback> create(@RequestBody Feedback f){
        Feedback created = svc.create(f);
        return ResponseEntity.created(URI.create("/api/feedbacks/"+created.getId())).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){ svc.delete(id); return ResponseEntity.noContent().build(); }
}
