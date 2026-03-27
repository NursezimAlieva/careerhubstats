package org.example.repository;

import org.example.model.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface statRepository extends JpaRepository<user, Long> {

    @Query(value = "SELECT " +
            "(SELECT COUNT(*) FROM mock_users) as totalUsers, " +
            "(SELECT COUNT(*) FROM mock_users WHERE role = 'student') as totalStudents, " +
            "(SELECT COUNT(*) FROM mock_users WHERE role = 'company') as totalCompanies",
            nativeQuery = true)
    Map<String, Object> getSyntheticStats();

    @Query(value = "SELECT " +
            "  main.registration_date, " +
            "  SUM(CASE WHEN main.role = 'student' THEN 1 ELSE 0 END) as studentCount, " +
            "  SUM(CASE WHEN main.role = 'company' THEN 1 ELSE 0 END) as companyCount, " +
            "  (SELECT COUNT(*) FROM mock_users WHERE is_intern = true AND internship_date = main.registration_date) as internCount, " +
            "  (SELECT COUNT(*) FROM mock_users WHERE is_job = true AND job_date = main.registration_date) as jobCount " +
            "FROM mock_users main " +
            "WHERE main.registration_date >= CURRENT_DATE - 7 " +
            "GROUP BY main.registration_date " +
            "ORDER BY main.registration_date ASC",
            nativeQuery = true)
    List<Map<String, Object>> getFullStatsByDay();
}