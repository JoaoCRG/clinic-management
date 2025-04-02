package com.ClinicManagement.Controllers;

import com.ClinicManagement.DTOs.PatientDTO;
import com.ClinicManagement.Entities.Patient;
import com.ClinicManagement.Services.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        List<PatientDTO> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id) {
        PatientDTO patient = patientService.getPatientById(id);
        return ResponseEntity.ok(patient);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PatientDTO>> getPatientsByName(@RequestParam String name) {
        List<PatientDTO> patients = patientService.getPatientsByName(name);
        return ResponseEntity.ok(patients);
    }

    @PostMapping("/add")
    public ResponseEntity<PatientDTO> createPatient(@RequestBody Patient patient) {
        PatientDTO createdPatient = patientService.createPatient(patient);
        return ResponseEntity.ok(createdPatient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<PatientDTO> editPatient(@PathVariable Long id, @RequestBody Patient patient) {
        PatientDTO editedPatientDTO = patientService.editPatient(id, patient);
        return ResponseEntity.ok(editedPatientDTO);
    }
}