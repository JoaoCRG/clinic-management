package com.ClinicManagement.Services;

import com.ClinicManagement.DTOs.PatientDTO;
import com.ClinicManagement.Entities.MedicalRegistry;
import com.ClinicManagement.Entities.Patient;
import com.ClinicManagement.Repositories.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
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
        System.out.println("Consent bytes length: " + (patient.getConsent() == null ? "null" : patient.getConsent().length));
        Patient savedPatient = patientRepository.saveAndFlush(patient);
        return new PatientDTO(savedPatient);
    }
    public Patient convertToEntity(PatientDTO dto, byte[] consent) {
        Patient patient = new Patient();
        patient.setName(dto.getName());
        patient.setPhoneNumber(dto.getPhoneNumber());
        patient.setCity(dto.getCity());
        patient.setBirthday(dto.getBirthday());
        patient.setConsent(consent);

        if (dto.getMedicalRegistry() != null) {
            MedicalRegistry registry = getMedicalRegistry(dto);

            patient.setMedicalRegistry(registry);
        }

        return patient;
    }

    private static MedicalRegistry getMedicalRegistry(PatientDTO dto) {
        MedicalRegistry registry = new MedicalRegistry();
        registry.setAllergies(dto.getMedicalRegistry().getAllergies());
        registry.setDiseases(dto.getMedicalRegistry().getDiseases());
        registry.setMedication(dto.getMedicalRegistry().getMedication());
        registry.setHospitalization(dto.getMedicalRegistry().getHospitalization());
        registry.setHospitalizationReason(dto.getMedicalRegistry().getHospitalizationReason());
        registry.setLastDoctorVisit(dto.getMedicalRegistry().getLastDoctorVisit());
        return registry;
    }


    public PatientDTO editPatient(Long id, Patient updatedPatient) {
        return patientRepository.findById(id).map(existingPatient -> {
            existingPatient.setName(updatedPatient.getName());
            existingPatient.setCity(updatedPatient.getCity());
            existingPatient.setPhoneNumber(updatedPatient.getPhoneNumber());
            existingPatient.setMedicalRegistry(updatedPatient.getMedicalRegistry());
            existingPatient.setConsent(updatedPatient.getConsent());

            Patient savedPatient = patientRepository.save(existingPatient);
            return new PatientDTO(savedPatient);
        }).orElseThrow(() -> new EntityNotFoundException("Não foi possível editar as informações do paciente." + id));
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}

