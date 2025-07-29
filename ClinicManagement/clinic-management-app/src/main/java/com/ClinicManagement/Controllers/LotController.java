package com.ClinicManagement.Controllers;

import com.ClinicManagement.DTOs.LotDTO;
import com.ClinicManagement.Entities.Lot;
import com.ClinicManagement.Services.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/lots")
public class LotController {

    private final LotService lotService;

    @Autowired
    public LotController(LotService lotService) {
        this.lotService = lotService;
    }

    @PostMapping("/add")
    public ResponseEntity<Lot> createLot(@RequestBody Lot lot) {
        Lot newLot = lotService.createLot(lot);
        return ResponseEntity.status(HttpStatus.CREATED).body(newLot);    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Lot> editLot(@PathVariable Long id, @RequestBody Lot updatedLot) {
        return ResponseEntity.ok(lotService.editLot(id,updatedLot));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLot(@PathVariable Long id) {
        lotService.deleteLot(id);
        return ResponseEntity.noContent().build();
    }
}
