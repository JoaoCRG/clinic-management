package com.ClinicManagement.Entities;

import jakarta.persistence.*;

@Entity
@Table
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String reference;
    private String lotNumber;
    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    public Lot() {}

    public Lot(Long id, String brand, String reference, String lotNumber, Appointment appointment) {
        this.id = id;
        this.brand = brand;
        this.reference = reference;
        this.lotNumber = lotNumber;
        this.appointment = appointment;
    }

    public Long getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getReference() {
        return reference;
    }

    public String getLotNumber() {
        return lotNumber;
    }

}

