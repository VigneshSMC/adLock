package com.fresh.health.adLock;

import com.fresh.health.service.serviceimpl.StudentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LockHandler {

    Logger logger = LoggerFactory.getLogger(LockHandler.class);

    private final AdvisoryLockRepository adLockRepo;

    LockHandler(AdvisoryLockRepository adLockRepo) {
        this.adLockRepo = adLockRepo;
    }

    public boolean lock(String token) {
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

    public void release(String token) {
        Optional<Object> release = Optional.ofNullable(adLockRepo.acquireLock(token));
        if (release.isPresent() && release.get().equals(1L)) {
            logger.info("Lock released for - {}", token);
        }
        else {
            logger.info("lock still exists");
        }
    }

}
