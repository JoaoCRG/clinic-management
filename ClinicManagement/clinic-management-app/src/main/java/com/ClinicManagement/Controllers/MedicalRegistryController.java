package com.ClinicManagement.Controllers;

import com.ClinicManagement.Entities.MedicalRegistry;
import com.ClinicManagement.Services.MedicalRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/medicalregistries")
public class MedicalRegistryController {

    private final MedicalRegistryService medicalRegistryService;

    @Autowired
    public MedicalRegistryController(MedicalRegistryService medicalRegistryService) {
        this.medicalRegistryService = medicalRegistryService;
    }

    @GetMapping
    public List<MedicalRegistry> getAllMedicalRegistry() {
        return medicalRegistryService.getAllMedicalRegistry();
    }

    @GetMapping("/id")
    public MedicalRegistry getMedicalRegistryById(Long id) {
        return medicalRegistryService.getMedicalRegistryById(id);
    }

    @PostMapping
    public ResponseEntity<MedicalRegistry> createMedicalRegistry(@RequestBody MedicalRegistry medicalRegistry) {
        MedicalRegistry newMedicalRegistry = medicalRegistryService.createMedicalRegistry(medicalRegistry);
        return  ResponseEntity.status(HttpStatus.CREATED).body(newMedicalRegistry);
    }
}
