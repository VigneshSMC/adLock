package com.fresh.health.service.serviceimpl;

import com.fresh.health.Entity.Student;
import com.fresh.health.adLock.AdvisoryLockRepository;
import com.fresh.health.adLock.LockHandler;
import com.fresh.health.repo.StudentRepo;
import com.fresh.health.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.locks.Lock;

@Service
public class StudentServiceImpl implements StudentService {

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepo studentRepo;
    private final LockHandler lockHandler;

    @Autowired
    public StudentServiceImpl(StudentRepo studentRepo, LockHandler lockHandler) {
        this.studentRepo = studentRepo;
        this.lockHandler = lockHandler;
    }

    @Override
    public void addStudent(String name) {
        boolean lock = false;
        try {
            lock = lockHandler.lock(name);
            if (studentRepo.studentExists(name)) {
                throw new RuntimeException("Student already exists");
            }

            Student studentEnt = new Student();
            studentEnt.setFirstName(name);
            var student = studentRepo.save(studentEnt);
        }
        catch (NullPointerException ex) {
            logger.info("Lock Exception");
        }
        finally {
            if (lock) lockHandler.release(name);
        }
    }
}
