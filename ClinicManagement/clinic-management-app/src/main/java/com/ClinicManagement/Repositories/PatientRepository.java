package com.ClinicManagement.Repositories;

import com.ClinicManagement.Entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient,Long> {

    List<Patient> findByNameContainingIgnoreCase(String name);
}
