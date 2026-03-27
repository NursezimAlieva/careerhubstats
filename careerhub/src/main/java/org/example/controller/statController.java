package org.example.controller;

import org.example.dto.statDTO;
import org.example.model.user;
import org.example.repository.statRepository;
import org.example.service.statService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/stats")
@CrossOrigin(origins = "http://localhost:5173")
public class statController {

    @Autowired private statService service;
    @Autowired private statRepository repository;

    @GetMapping
    public statDTO getStats() { return service.getStats(); }

    @GetMapping("/users")
    public List<user> getAllUsers() { return repository.findAll(); }

    @PostMapping("/users")
    public void createUser(@RequestBody Map<String, String> payload) {
        service.createNewUser(payload.get("name"), payload.get("role"));
    }

    @PutMapping("/intern/{id}")
    public void acceptToInternship(@PathVariable Long id) {
        repository.findById(id).ifPresent(u -> {
            u.setIntern(true);
            u.setInternshipDate(LocalDate.now());
            repository.save(u);
        });
    }

    @PutMapping("/job/{id}")
    public void acceptToJob(@PathVariable Long id) {
        repository.findById(id).ifPresent(u -> {
            u.setJob(true);
            u.setJobDate(LocalDate.now());
            repository.save(u);
        });
    }
}