package com.ClinicManagement.Entities;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
    
@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    
    @JoinColumn(name = "medical_registry_id", referencedColumnName = "id")
    private MedicalRegistry medicalRegistry;
    private String phoneNumber;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Appointment> appointmentHistory = new ArrayList<>();
    private String city;
    private Date birthday;


public Patient() {}

    public Patient(String name, Long id, MedicalRegistry medicalRegistry, String phoneNumber, List<Appointment> appointmentHistory, String city, Date birthday) {
        this.name = name;
        this.id = id;
        this.medicalRegistry = medicalRegistry;
        this.phoneNumber = phoneNumber;
        this.appointmentHistory = appointmentHistory;
        this.city = city;
        this.birthday = birthday;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MedicalRegistry getMedicalRegistry() {
        return medicalRegistry;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMedicalRegistry(MedicalRegistry medicalRegistry) {
        this.medicalRegistry = medicalRegistry;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Appointment> getAppointmentHistory() {
        return appointmentHistory;
    }

    public void setAppointmentHistory(List<Appointment> appointmentHistory) {
        this.appointmentHistory = appointmentHistory;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
