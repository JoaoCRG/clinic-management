package com.ClinicManagement.Services;

import com.ClinicManagement.DTOs.PatientDTO;
import com.ClinicManagement.Entities.Patient;
import com.ClinicManagement.Repositories.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(PatientDTO::new)
                .collect(Collectors.toList());
    }

    public List<PatientDTO> getPatientsByName(String name) {
        return patientRepository.findByNameContainingIgnoreCase(name).stream()
                .map(PatientDTO::new)
                .collect(Collectors.toList());
    }

    public PatientDTO getPatientById(Long id) {
        return patientRepository.findById(id)
                .map(PatientDTO::new)
                .orElseThrow(() -> new NullPointerException("Este paciente não existe."));
    }

    public PatientDTO createPatient(Patient patient) {
        Patient savedPatient = patientRepository.saveAndFlush(patient);
        return new PatientDTO(savedPatient);
    }

    public PatientDTO editPatient(Long id,Patient updatedPatient) {
        return patientRepository.findById(id).map(existingPatient -> {
            existingPatient.setName(updatedPatient.getName());
            existingPatient.setCity(updatedPatient.getCity());
            existingPatient.setPhoneNumber(updatedPatient.getPhoneNumber());
            existingPatient.setMedicalRegistry(updatedPatient.getMedicalRegistry());

            Patient savedPatient = patientRepository.save(existingPatient);
            return new PatientDTO(savedPatient);
        }).orElseThrow(() -> new EntityNotFoundException("Não foi possivel editar as informações" +  id));
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}

