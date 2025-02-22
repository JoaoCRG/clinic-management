package com.ClinicManagement.Repositories;

import com.ClinicManagement.Entities.Pacient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PacientRepository extends JpaRepository<Pacient,Long> {

    List<Pacient> findByNameContainingIgnoreCase(String name);
}
