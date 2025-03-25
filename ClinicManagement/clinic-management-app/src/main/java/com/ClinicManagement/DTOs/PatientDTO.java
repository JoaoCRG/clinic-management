package com.ClinicManagement.DTOs;

import com.ClinicManagement.Entities.Patient;

import java.util.List;

public class PatientDTO {
    private Long id;
    private String name;
    private String phoneNumber;
    private MedicalRegistryDTO medicalRegistry;
    private List<AppointmentDTO> appointmentHistory;

    public PatientDTO(Patient patient) {
        this.id = patient.getId();
        this.name = patient.getName();
        this.phoneNumber = patient.getPhoneNumber();
        this.medicalRegistry = new MedicalRegistryDTO(patient.getMedicalRegistry());
        this.appointmentHistory = patient.getAppointmentHistory().stream()
                .map(AppointmentDTO::new)
                .toList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AppointmentDTO> getAppointmentHistory() {
        return appointmentHistory;
    }

    public void setAppointmentHistory(List<AppointmentDTO> appointmentHistory) {
        this.appointmentHistory = appointmentHistory;
    }

    public MedicalRegistryDTO getMedicalRegistry() {
        return medicalRegistry;
    }

    public void setMedicalRegistry(MedicalRegistryDTO medicalRegistry) {
        this.medicalRegistry = medicalRegistry;
    }
}
