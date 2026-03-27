package org.example.service;

import org.example.dto.statDTO;
import org.example.model.user;
import org.example.repository.statRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class statService {

    @Autowired private statRepository repository;

    public statDTO getStats() {
        Map<String, Object> totals = repository.getSyntheticStats();
        List<Map<String, Object>> dailyData = repository.getFullStatsByDay();

        List<String> labels = new ArrayList<>();
        List<Integer> students = new ArrayList<>();
        List<Integer> companies = new ArrayList<>();
        List<Integer> interns = new ArrayList<>();
        List<Integer> jobs = new ArrayList<>();

        for (Map<String, Object> row : dailyData) {
            labels.add(row.get("registration_date").toString());
            students.add(((Number) row.getOrDefault("studentCount", 0)).intValue());
            companies.add(((Number) row.getOrDefault("companyCount", 0)).intValue());
            interns.add(((Number) row.getOrDefault("internCount", 0)).intValue());
            jobs.add(((Number) row.getOrDefault("jobCount", 0)).intValue());
        }

        return new statDTO(
                ((Number) totals.get("totalUsers")).longValue(),
                ((Number) totals.get("totalStudents")).longValue(),
                ((Number) totals.get("totalCompanies")).longValue(),
                labels, students, companies, interns, jobs
        );
    }

    public void createNewUser(String name, String role) {
        user newUser = new user();
        newUser.setName(name);
        newUser.setRole(role);
        repository.save(newUser);
    }
}