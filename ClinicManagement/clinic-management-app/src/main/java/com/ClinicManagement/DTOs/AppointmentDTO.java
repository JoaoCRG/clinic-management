package com.ClinicManagement.DTOs;

import com.ClinicManagement.Entities.Appointment;
import java.time.LocalDate;
import java.util.List;

public class AppointmentDTO {
    private Long id;
    private LocalDate date;
    private String procedure;
    private List<LotDTO> lots;

    public AppointmentDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.date = appointment.getDate();
        this.procedure = appointment.getProcedure();
        this.lots = appointment.getLots().stream()
                .map(LotDTO::new)
                .toList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public List<LotDTO> getLots() {
        return lots;
    }

    public void setLots(List<LotDTO> lots) {
        this.lots = lots;
    }
}
