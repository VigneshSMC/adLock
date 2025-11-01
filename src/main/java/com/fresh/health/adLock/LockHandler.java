package com.fresh.health.adLock;

import com.fresh.health.request.StudentRequest;
import com.fresh.health.service.serviceimpl.StudentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class LockHandler {

    Logger logger = LoggerFactory.getLogger(LockHandler.class);

    private final AdvisoryLockRepository adLockRepo;

    LockHandler(AdvisoryLockRepository adLockRepo) {
        this.adLockRepo = adLockRepo;
    }

    public int createToken(StudentRequest request) {
        return Objects.hashCode(request.getStudentId() + request.getFirstName() + request.getSecondName() + request.getGrade());
    }

    public boolean lock(int token) {
        Optional<Object> lock = Optional.ofNullable(adLockRepo.acquireLock(token));
        if (lock.isPresent() && lock.get().equals(1L)) {
            logger.info("Lock obtained for - {}", token);
            return true;
        }
        else {
            logger.info("another thread is active");
        }
        return false;
    }

    public void release(int token) {
        Optional<Object> release = Optional.ofNullable(adLockRepo.releaseLock(token));
        if (release.isPresent() && release.get().equals(1L)) {
            logger.info("Lock released for - {}", token);
        }
        else {
            logger.info("lock still exists");
        }
    }

}
