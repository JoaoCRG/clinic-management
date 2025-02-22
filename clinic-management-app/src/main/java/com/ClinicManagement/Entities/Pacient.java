package com.ClinicManagement.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.eclipse.angus.mail.iap.ByteArray;

import java.io.File;

@Entity
@Table
public class Pacient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne
    @JoinColumn(name = "medical_registry_id")
    @JsonIgnore
    private MedicalRegistry medicalRegistry;
    private String phoneNumber;


public Pacient() {}

    public Pacient(String name, Long id, MedicalRegistry medicalRegistry, String phoneNumber) {
        this.name = name;
        this.id = id;
        this.medicalRegistry = medicalRegistry;
        this.phoneNumber = phoneNumber;
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

}
