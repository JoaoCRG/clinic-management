package com.ClinicManagement.Repositories;

import com.ClinicManagement.Entities.MedicalRegistry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRegistryRepository extends JpaRepository<MedicalRegistry, Long> {}
