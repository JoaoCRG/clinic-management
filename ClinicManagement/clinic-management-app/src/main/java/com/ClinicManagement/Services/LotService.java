package com.ClinicManagement.Services;

import com.ClinicManagement.Entities.Lot;
import com.ClinicManagement.Repositories.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LotService {

    private final LotRepository lotRepository;

    @Autowired
    public LotService(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }

    public List<Lot> getAllLots(){
        return lotRepository.findAll();
    }

    public Lot getLotById(Long id) {
        return lotRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("Este lote não existe"));
    }

    public Lot createLot(Lot lot) {
        return lotRepository.save(lot);
    }

    public void deleteLot(Long id) {
        lotRepository.deleteById(id);
    }
}
