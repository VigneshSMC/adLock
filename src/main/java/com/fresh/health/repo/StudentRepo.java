package com.fresh.health.repo;

import com.fresh.health.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {

    @Query(nativeQuery = true, value = "select * from vader.students s where s.student_id = :studentId")
    public List<Student> findByName(String studentId);

    default public boolean studentExists(String studentId) {
        return !findByName(studentId).isEmpty();
    }
}
