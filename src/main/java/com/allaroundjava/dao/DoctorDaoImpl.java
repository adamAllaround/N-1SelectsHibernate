package com.allaroundjava.dao;

import com.allaroundjava.model.Doctor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.Optional;
import java.util.function.Consumer;

public class DoctorDaoImpl implements DoctorDao {
    private static final Logger log = LogManager.getLogger(DoctorDaoImpl.class);
    protected final EntityManagerFactory emf;

    DoctorDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void executeInTransaction(Consumer<EntityManager> consumer) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        consumer.accept(entityManager);

        transaction.commit();
        entityManager.close();
    }

    @Override
    public Optional<Doctor> getById(Long id) {
        log.debug("Fetching {} with id {} from database", getClass(), id);
        EntityManager entityManager = emf.createEntityManager();
        return Optional.ofNullable(entityManager.find(Doctor.class, id));
    }

    @Override
    public void persist(Doctor item) {
        log.debug("Persisting {} ", item.getClass(), item.getClass());
        executeInTransaction(entityManager -> entityManager.persist(item));
    }

}
