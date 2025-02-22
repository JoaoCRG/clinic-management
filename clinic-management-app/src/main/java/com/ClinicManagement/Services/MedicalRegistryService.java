package com.ClinicManagement.Services;

import com.ClinicManagement.Entities.MedicalRegistry;
import com.ClinicManagement.Repositories.MedicalRegistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedicalRegistryService {

    private final MedicalRegistryRepository medicalRegistryRepository;

    @Autowired
    public MedicalRegistryService(MedicalRegistryRepository medicalRegistryRepository) {
        this.medicalRegistryRepository = medicalRegistryRepository;
    }

    public List<MedicalRegistry> getAllMedicalRegistry() {
        return medicalRegistryRepository.findAll();
    }

    public MedicalRegistry getMedicalRegistryById(Long id) {
        return medicalRegistryRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("Este registo médico não existe."));
    }

    public MedicalRegistry createMedicalRegistry(MedicalRegistry medicalRegistry) {
        return medicalRegistryRepository.save(medicalRegistry);
    }
}
