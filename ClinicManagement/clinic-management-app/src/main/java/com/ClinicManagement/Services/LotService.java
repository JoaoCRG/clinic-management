package com.ClinicManagement.Services;

import com.ClinicManagement.Entities.Appointment;
import com.ClinicManagement.Entities.Lot;
import com.ClinicManagement.Repositories.AppointmentRepository;
import com.ClinicManagement.Repositories.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LotService {

    private final LotRepository lotRepository;
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public LotService(LotRepository lotRepository, AppointmentRepository appointmentRepository) {
        this.lotRepository = lotRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public Lot getLotById(Long id) {
        return lotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lot não encontrado"));
    }

    public Lot createLot(Lot lot) {
        lot.setBrand(lot.getBrand());
        lot.setLotNumber(lot.getLotNumber());
        lot.setRef(lot.getRef());
        lot.setModel(lot.getModel());
        lot.setValidityStart(lot.getValidityStart());
        lot.setValidityEnd(lot.getValidityEnd());

        Appointment appointment = appointmentRepository.findById(lot.getAppointment().getId())
                .orElseThrow(() -> new RuntimeException("Appointment não encontrado"));

        lot.setAppointment(appointment);

        return lotRepository.saveAndFlush(lot);
    }

    public Lot editLot(Long id, Lot editedLot) {
        Lot existingLot = getLotById(id);
        existingLot.setBrand(editedLot.getBrand());
        existingLot.setLotNumber(editedLot.getLotNumber());
        existingLot.setRef(editedLot.getRef());
        existingLot.setModel(editedLot.getModel());
        existingLot.setValidityStart(editedLot.getValidityStart());
        existingLot.setValidityEnd(editedLot.getValidityEnd());
        return lotRepository.save(existingLot);
    }

    public void deleteLot(Long id) {
        lotRepository.deleteById(id);
    }
}
