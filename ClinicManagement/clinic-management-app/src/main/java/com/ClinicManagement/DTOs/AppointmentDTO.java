package com.ClinicManagement.DTOs;

import com.ClinicManagement.Entities.Appointment;
import com.ClinicManagement.Entities.Lot;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentDTO {
    private Long id;
    private LocalDateTime date;
    private String procedure;
    private List<LotDTO> lots;
    private Integer price;
    private String patientName;

    public AppointmentDTO(){}

    public AppointmentDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.date = appointment.getDate();
        this.procedure = appointment.getProcedure();
        this.price = appointment.getPrice();
        this.lots = appointment.getLots().stream()
                .map(LotDTO::new)
                .toList();
        this.patientName = appointment.getPatient().getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public List<LotDTO> getLots() {
        return lots;
    }

    public void setLots(List<LotDTO> lots) {
        this.lots = lots;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
}
