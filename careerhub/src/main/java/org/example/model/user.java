package org.example.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "mock_users")
public class user {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String role;
    private boolean isIntern = false;
    private boolean isJob = false;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "internship_date")
    private LocalDate internshipDate;

    @Column(name = "job_date")
    private LocalDate jobDate;

    public user() {
        this.registrationDate = LocalDate.now();
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public boolean isIntern() { return isIntern; }
    public void setIntern(boolean intern) { isIntern = intern; }
    public boolean isJob() { return isJob; }
    public void setJob(boolean job) { isJob = job; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }
    public LocalDate getInternshipDate() { return internshipDate; }
    public void setInternshipDate(LocalDate internshipDate) { this.internshipDate = internshipDate; }
    public LocalDate getJobDate() { return jobDate; }
    public void setJobDate(LocalDate jobDate) { this.jobDate = jobDate; }
}