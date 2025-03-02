package com.ClinicManagement.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String procedure;
    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Lot> lots;
    @ManyToOne
    @JoinColumn(name = "medical_registry_id", nullable = false)
    @JsonIgnore
    private MedicalRegistry medicalRegistry;
    @ManyToOne
    @JoinColumn(name = "pacient_id", nullable = false)
    @JsonBackReference
    private Pacient pacient;

    public Appointment() {}

    public Appointment(Long id, LocalDate date, String procedure, List<Lot> lots, MedicalRegistry medicalRegistry,Pacient pacient) {
        this.id = id;
        this.date = date;
        this.procedure = procedure;
        this.lots = lots;
        this.medicalRegistry = medicalRegistry;
        this.pacient = pacient;
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

    public MedicalRegistry getMedicalRegistry() {
        return medicalRegistry;
    }

    public Pacient getPacient() {
        return pacient;
    }

    public void setPacient(Pacient pacient) {
        this.pacient = pacient;
    }

    public void setMedicalRegistry(MedicalRegistry medicalRegistry) {
        this.medicalRegistry = medicalRegistry;
    }
}
