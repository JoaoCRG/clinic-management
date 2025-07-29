package com.ClinicManagement.Entities;

import com.ClinicManagement.DTOs.AppointmentDTO;
import com.ClinicManagement.DTOs.LotDTO;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "lots")
public class Lot extends LotDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String lotNumber;
    private String ref;
    private String model;
    private LocalDate validityStart;
    private LocalDate validityEnd;
    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;


    public Lot(){}

    public Lot(Long id, String brand, String ref, String lotNumber, String model, LocalDate validityStart, LocalDate validityEnd, Appointment appointment) {
        this.id = id;
        this.brand = brand;
        this.ref = ref;
        this.lotNumber = lotNumber;
        this.model = model;
        this.validityStart = validityStart;
        this.validityEnd = validityEnd;
        this.appointment = appointment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public LocalDate getValidityStart() {
        return validityStart;
    }

    public void setValidityStart(LocalDate validityStart) {
        this.validityStart = validityStart;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public LocalDate getValidityEnd() {
        return validityEnd;
    }

    public void setValidityEnd(LocalDate validityEnd) {
        this.validityEnd = validityEnd;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Appointment getAppointment() {
        return appointment;
    }
}
