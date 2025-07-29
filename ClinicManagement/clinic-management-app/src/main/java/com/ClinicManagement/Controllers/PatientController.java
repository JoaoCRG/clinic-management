package com.ClinicManagement.Controllers;

import com.ClinicManagement.DTOs.PatientDTO;
import com.ClinicManagement.Entities.Patient;
import com.ClinicManagement.Services.PatientService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
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

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PatientDTO> createPatient(
            @RequestPart("patient") PatientDTO patientDTO,
            @RequestPart(value = "consent", required = false) MultipartFile consentFile
    ) throws IOException {
        byte[] consentBytes = null;
        if(!(consentFile == null)) {
            consentBytes = consentFile.getBytes();
        }
        Patient patient = patientService.convertToEntity(patientDTO, consentBytes);
        PatientDTO createdPatient = patientService.createPatient(patient);
        return ResponseEntity.ok(createdPatient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/edit/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PatientDTO> editPatient(
            @PathVariable Long id,
            @RequestPart("patient") PatientDTO patientDTO,
            @RequestPart(value = "consent", required = false) MultipartFile consentFile
    ) throws IOException {
        byte[] consentBytes = null;
        if (consentFile != null && !consentFile.isEmpty()) {
            consentBytes = consentFile.getBytes();
        }

        Patient updatedPatient = patientService.convertToEntity(patientDTO, consentBytes);
        PatientDTO updatedDTO = patientService.editPatient(id, updatedPatient);
        return ResponseEntity.ok(updatedDTO);
    }

}