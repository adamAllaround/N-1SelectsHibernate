package com.allaroundjava.dao;

import com.allaroundjava.model.Appointment;
import com.allaroundjava.model.Doctor;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class DoctorDaoImplTest {
    private EntityManagerFactory emf;
    private DoctorDao doctorDao;

    public DoctorDaoImplTest() {
        this.emf = Persistence.createEntityManagerFactory("hibernateNPlusOneSelects");
        doctorDao = new DoctorDaoImpl(emf);
    }

    @Test
    public void whenLazyLoadingCollection_andAccessingItems_thenNPlusOneSelects() {
        createTestData();

        doctorDao.executeInTransaction(entityManager -> {
            List<Doctor> doctors = entityManager.createQuery("select d from Doctor d", Doctor.class).getResultList();
            for (Doctor doctor : doctors) {
                Assert.assertTrue(doctor.getAppointments().size() > 0);
            }
        });
    }

    private void createTestData() {
        doctorDao.executeInTransaction(entityManager -> {

            for (int i = 0; i < 10; i++) {
                Doctor doctor = new Doctor("Doctor John");
                entityManager.persist(doctor);
                Appointment appointment1 = new Appointment();
                appointment1.setAppointmentTime(LocalDateTime.of(2019, 6, 10, i, 0, 0));
                appointment1.setDoctor(doctor);
                entityManager.persist(appointment1);

                Appointment appointment2 = new Appointment();
                appointment2.setAppointmentTime(LocalDateTime.of(2019, 6, 11, 10 + i, 0, 0));
                appointment2.setDoctor(doctor);
                entityManager.persist(appointment2);

            }
        });
    }


}