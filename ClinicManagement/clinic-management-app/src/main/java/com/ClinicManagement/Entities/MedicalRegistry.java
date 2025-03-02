package com.ClinicManagement.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table
public class MedicalRegistry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date lastDoctorVisit;
    private Boolean hospitalization;
    private String hospitalizationReason;
    private String medication;
    private String diseases;



    public MedicalRegistry() {
    }

    public MedicalRegistry(Long id,Date lastDoctorVisit, Boolean hospitalization, String medication, String hospitalizationReason, String diseases) {
        this.id = id;
        this.lastDoctorVisit = lastDoctorVisit;
        this.hospitalization = hospitalization;
        this.medication = medication;
        this.hospitalizationReason = hospitalizationReason;
        this.diseases = diseases;

    }

    public Long getId() {
        return id;
    }

    public Date getLastDoctorVisit() {
        return lastDoctorVisit;
    }

    public Boolean getHospitalization() {
        return hospitalization;
    }

    public String getHospitalizationReason() {
        return hospitalizationReason;
    }

    public String getMedication() {
        return medication;
    }

    public String getDiseases() {
        return diseases;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLastDoctorVisit(Date lastDoctorVisit) {
        this.lastDoctorVisit = lastDoctorVisit;
    }

    public void setHospitalization(Boolean hospitalization) {
        this.hospitalization = hospitalization;
    }

    public void setHospitalizationReason(String hospitalizationReason) {
        this.hospitalizationReason = hospitalizationReason;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }
}
