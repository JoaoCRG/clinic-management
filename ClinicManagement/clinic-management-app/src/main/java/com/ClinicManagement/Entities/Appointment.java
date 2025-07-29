package com.ClinicManagement.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private String procedure;
    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lot> lots = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    private Integer price;

    public Appointment() {}

    public Appointment(Long id, LocalDateTime date, String procedure, List<Lot> lots, Patient patient, Integer price) {
        this.id = id;
        this.date = date;
        this.procedure = procedure;
        this.lots = lots;
        this.patient = patient;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getProcedure() {
        return procedure;
    }

    public List<Lot> getLots() {
        return lots;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public void setLots(List<Lot> lots) {
        this.lots = lots;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
