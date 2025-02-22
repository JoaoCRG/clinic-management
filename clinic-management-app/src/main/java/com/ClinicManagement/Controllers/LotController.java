package com.ClinicManagement.Controllers;

import com.ClinicManagement.Entities.Lot;
import com.ClinicManagement.Services.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/lots")
public class LotController {

    private final LotService lotService;

    @Autowired
    public LotController(LotService lotService) {
        this.lotService = lotService;
    }

    @GetMapping
    public List<Lot> getAllLots() {
        return lotService.getAllLots();
    }

    @GetMapping("/id")
    public Lot getLotById(Long id) {
        return lotService.getLotById(id);
    }

    @PostMapping
    public ResponseEntity<Lot> createLot(@RequestBody Lot lot) {
        Lot newLot = lotService.createLot(lot);
        return ResponseEntity.status(HttpStatus.CREATED).body(newLot);
    }
}
