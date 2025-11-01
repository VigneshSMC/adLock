package com.fresh.health.repo;

import com.fresh.health.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {

    @Query(nativeQuery = true, value = "select * from vader.students s where s.name = :name")
    public List<Student> findByName(String name);

    default public boolean studentExists(String name) {
        return !findByName(name).isEmpty();
    }
}
