package com.ClinicManagement.Controllers;

import com.ClinicManagement.DTOs.AppointmentDTO;
import com.ClinicManagement.Entities.Appointment;
import com.ClinicManagement.Services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }


    @PostMapping("/add")
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody Appointment appointment) {
        AppointmentDTO newAppointment = appointmentService.createAppointment(appointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAppointment);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<AppointmentDTO> editAppointment(@PathVariable Long id, @RequestBody Appointment updatedAppointment) {
        return ResponseEntity.ok(appointmentService.editAppointment(id, updatedAppointment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}
