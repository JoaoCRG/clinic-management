package com.ClinicManagement.DTOs;

import com.ClinicManagement.Entities.MedicalRegistry;
import java.sql.Date;

public class MedicalRegistryDTO {
    private Long id;
    private Date lastDoctorVisit;
    private Boolean hospitalization;
    private String hospitalizationReason;
    private String medication;
    private String diseases;
    private String allergies;

    public MedicalRegistryDTO (){}

    public MedicalRegistryDTO(MedicalRegistry medicalRegistry) {
        this.id = medicalRegistry.getId();
        this.lastDoctorVisit = medicalRegistry.getLastDoctorVisit();
        this.hospitalization = medicalRegistry.getHospitalization();
        this.hospitalizationReason = medicalRegistry.getHospitalizationReason();
        this.medication = medicalRegistry.getMedication();
        this.diseases = medicalRegistry.getDiseases();
        this.allergies = medicalRegistry.getAllergies();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiseases() {
        return diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getHospitalizationReason() {
        return hospitalizationReason;
    }

    public void setHospitalizationReason(String hospitalizationReason) {
        this.hospitalizationReason = hospitalizationReason;
    }

    public Boolean getHospitalization() {
        return hospitalization;
    }

    public void setHospitalization(Boolean hospitalization) {
        this.hospitalization = hospitalization;
    }

    public Date getLastDoctorVisit() {
        return lastDoctorVisit;
    }

    public void setLastDoctorVisit(Date lastDoctorVisit) {
        this.lastDoctorVisit = lastDoctorVisit;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }
}
