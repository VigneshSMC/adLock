package com.fresh.health.service.serviceimpl;

import com.fresh.health.Entity.Student;
import com.fresh.health.adLock.LockHandler;
import com.fresh.health.repo.StudentRepo;
import com.fresh.health.request.StudentRequest;
import com.fresh.health.service.StudentService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepo studentRepo;
    private final LockHandler lockHandler;
    private final ModelMapper mapper;

    @Autowired
    public StudentServiceImpl(StudentRepo studentRepo, LockHandler lockHandler, ModelMapper mapper) {
        this.studentRepo = studentRepo;
        this.lockHandler = lockHandler;
        this.mapper = mapper;
    }

    @Override
    public void addStudent(StudentRequest request) {
        boolean lock = false;
        int token = lockHandler.createToken(request);
        try {
            lock = lockHandler.lock(token);
            if (studentRepo.studentExists(request.getStudentId())) {
                throw new RuntimeException("Student already exists");
            }

            Student studentEnt = mapper.map(request, Student.class);
            var student = studentRepo.save(studentEnt);
        }
        catch (NullPointerException ex) {
            logger.info("Lock Exception");
        }
        finally {
            if (lock) lockHandler.release(token);
        }
    }
}
