package com.fresh.health.adLock;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static org.yaml.snakeyaml.nodes.Tag.STR;

@Repository
public class AdvisoryLockRepository {

    private final EntityManager entityManager;

    @Autowired
    public AdvisoryLockRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Object acquireLock(String name) {
        return entityManager.createNativeQuery("select GET_LOCK(:name, 5)").setParameter("name", name).getSingleResult();
    }

    @Transactional
    public Object releaseLock(String name) {
        return entityManager.createNativeQuery("select RELEASE_LOCK(:name, 5)").setParameter("name", name).getSingleResult();
    }

}
