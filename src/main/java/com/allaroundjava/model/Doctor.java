package com.allaroundjava.model;

import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
public class Doctor extends ModelBase {
    private String name;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    private Collection<Appointment> appointments;

    Doctor() {
    }

    public Doctor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Collection<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Collection<Appointment> appointments) {
        this.appointments = appointments;
    }
}
