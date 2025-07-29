package com.ClinicManagement.Services;

import com.ClinicManagement.DTOs.AppointmentDTO;
import com.ClinicManagement.Entities.Appointment;
import com.ClinicManagement.Entities.Lot;
import com.ClinicManagement.Repositories.AppointmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final LotService lotService;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, LotService lotService) {
        this.appointmentRepository = appointmentRepository;
        this.lotService = lotService;
    }

    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(AppointmentDTO::new)
                .collect(Collectors.toList());
    }

    public AppointmentDTO createAppointment(Appointment appointment) {
        Appointment savedAppointment = appointmentRepository.saveAndFlush(appointment);
        return new AppointmentDTO(savedAppointment);
    }

    public AppointmentDTO editAppointment(Long id, Appointment updatedAppointment) {
       return appointmentRepository.findById(id).map( existingAppointment -> {
           existingAppointment.setDate(updatedAppointment.getDate());
           existingAppointment.setProcedure(updatedAppointment.getProcedure());
           existingAppointment.setPrice(updatedAppointment.getPrice());

           for(Lot lot : updatedAppointment.getLots()) {
               lot.setAppointment(existingAppointment);
               existingAppointment.getLots().add(lot);
                   }

           Appointment savedAppointment = appointmentRepository.save(existingAppointment);
           return new AppointmentDTO(savedAppointment);
       }).orElseThrow(() -> new EntityNotFoundException("Não foi possível editar as informações da consulta."));
    }
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}
