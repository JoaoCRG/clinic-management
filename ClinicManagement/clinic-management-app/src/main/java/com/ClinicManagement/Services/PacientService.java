package com.ClinicManagement.Services;

import com.ClinicManagement.Entities.Pacient;
import com.ClinicManagement.Repositories.PacientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PacientService {

    private final PacientRepository pacientRepository;

    @Autowired
    public PacientService(PacientRepository pacientRepository) {
        this.pacientRepository = pacientRepository;
    }

    public List<Pacient> getAllPacients() {
        return pacientRepository.findAll();
    }

    public List<Pacient> getPacientByName(String name) {
        return pacientRepository.findByNameContainingIgnoreCase(name);
    }

    public Pacient getPacientById(Long id){
        return pacientRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("Este paciente não existe."));
    }

    public Pacient createPacient(Pacient pacient) {
        return pacientRepository.saveAndFlush(pacient);
    }

    public void deletePacient(Long id) {
        pacientRepository.deleteById(id);
    }
}
