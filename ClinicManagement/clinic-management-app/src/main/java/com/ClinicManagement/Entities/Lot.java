package com.ClinicManagement.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "lots")
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String reference;
    private String lotNumber;
    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
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

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public String getLotNumber() {
        return lotNumber;
    }

}

