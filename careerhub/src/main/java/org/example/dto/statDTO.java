package org.example.dto;
import java.util.List;

public class statDTO {
    private long totalUsers;
    private long totalStudents;
    private long totalCompanies;
    private List<String> chartLabels;
    private List<Integer> studentData;
    private List<Integer> companyData;
    private List<Integer> internshipData;
    private List<Integer> jobData;

    public statDTO() {}

    public statDTO(long totalUsers, long totalStudents, long totalCompanies,
                   List<String> chartLabels, List<Integer> studentData,
                   List<Integer> companyData, List<Integer> internshipData, List<Integer> jobData) {
        this.totalUsers = totalUsers;
        this.totalStudents = totalStudents;
        this.totalCompanies = totalCompanies;
        this.chartLabels = chartLabels;
        this.studentData = studentData;
        this.companyData = companyData;
        this.internshipData = internshipData;
        this.jobData = jobData;
    }

    // Геттеры и сеттеры (создай для всех полей, включая jobData)
    public long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }
    public long getTotalStudents() { return totalStudents; }
    public void setTotalStudents(long totalStudents) { this.totalStudents = totalStudents; }
    public long getTotalCompanies() { return totalCompanies; }
    public void setTotalCompanies(long totalCompanies) { this.totalCompanies = totalCompanies; }
    public List<String> getChartLabels() { return chartLabels; }
    public void setChartLabels(List<String> chartLabels) { this.chartLabels = chartLabels; }
    public List<Integer> getStudentData() { return studentData; }
    public void setStudentData(List<Integer> studentData) { this.studentData = studentData; }
    public List<Integer> getCompanyData() { return companyData; }
    public void setCompanyData(List<Integer> companyData) { this.companyData = companyData; }
    public List<Integer> getInternshipData() { return internshipData; }
    public void setInternshipData(List<Integer> internshipData) { this.internshipData = internshipData; }
    public List<Integer> getJobData() { return jobData; }
    public void setJobData(List<Integer> jobData) { this.jobData = jobData; }
}