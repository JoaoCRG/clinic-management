package com.ClinicManagement.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String procedure;
    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
    private List<Lot> lots;
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    public Appointment() {}

    public Appointment(Long id, LocalDate date, String procedure, List<Lot> lots, Patient patient) {
        this.id = id;
        this.date = date;
        this.procedure = procedure;
        this.lots = lots;
        this.patient = patient;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getProcedure() {
        return procedure;
    }

    public List<Lot> getLots() {
        return lots;
    }

    public void setDate(LocalDate date) {
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

    public Patient getPacient() {
        return patient;
    }

    public void setPacient(Patient patient) {
        this.patient = patient;
    }

}
