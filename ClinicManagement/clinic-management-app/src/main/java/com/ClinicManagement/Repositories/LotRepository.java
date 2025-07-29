package com.ClinicManagement.Repositories;

import com.ClinicManagement.Entities.Lot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotRepository extends JpaRepository<Lot,Long> {
}
