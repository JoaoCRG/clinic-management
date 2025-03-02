package com.ClinicManagement.Controllers;

import com.ClinicManagement.Entities.Pacient;
import com.ClinicManagement.Services.PacientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8000")
@RestController
@RequestMapping("/pacients")
public class PacientController {

    private final PacientService pacientService;

    @Autowired
    public PacientController(PacientService pacientService) {
        this.pacientService = pacientService;
    }

    @GetMapping("/all")
    public List<Pacient> getAllPacients() {
        return pacientService.getAllPacients();
    }

    @GetMapping("/id")
    public Pacient getPacientById(Long id) {
        return pacientService.getPacientById(id);
    }

    @PostMapping
    public ResponseEntity<Pacient> createPacient(@RequestBody Pacient pacient) {
        Pacient newPacient = pacientService.createPacient(pacient);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPacient);
    }
}
