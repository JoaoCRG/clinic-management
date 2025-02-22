package com.ClinicManagement.Entities;

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
    @OneToMany(mappedBy = "medicalRegistry", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Appointment> appointmentHistory;



    public MedicalRegistry() {
    }

    public MedicalRegistry(Long id,Date lastDoctorVisit, Boolean hospitalization, String medication, String hospitalizationReason, String diseases, List<Appointment> appointmentHistory) {
        this.id = id;
        this.lastDoctorVisit = lastDoctorVisit;
        this.hospitalization = hospitalization;
        this.medication = medication;
        this.hospitalizationReason = hospitalizationReason;
        this.diseases = diseases;
        this.appointmentHistory = appointmentHistory;
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

    public List<Appointment> getAppointmentHistory() {
        return appointmentHistory;
    }
}
