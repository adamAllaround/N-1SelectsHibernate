package com.allaroundjava.dao;

import com.allaroundjava.model.Doctor;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.function.Consumer;

public interface DoctorDao {
    Optional<Doctor> getById(Long id);

    void persist(Doctor item);

    void executeInTransaction(Consumer<EntityManager> consumer);
}
