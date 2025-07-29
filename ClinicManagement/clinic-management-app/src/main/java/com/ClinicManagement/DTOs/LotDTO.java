package com.ClinicManagement.DTOs;

import com.ClinicManagement.Entities.Lot;
import java.time.LocalDate;

public class LotDTO {
    private Long id;
    private String brand;
    private String lotNumber;
    private String ref;
    private String model;
    private LocalDate validityStart;
    private LocalDate validityEnd;

    public LotDTO() {}

    public LotDTO(Lot lot) {
        this.id = lot.getId();
        this.brand = lot.getBrand();
        this.lotNumber = lot.getLotNumber();
        this.ref = lot.getRef();
        this.model = lot.getModel();
        this.validityStart = lot.getValidityStart();
        this.validityEnd = lot.getValidityEnd();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public LocalDate getValidityStart() {
        return validityStart;
    }

    public void setValidityStart(LocalDate validityStart) {
        this.validityStart = validityStart;
    }

    public LocalDate getValidityEnd() {
        return validityEnd;
    }

    public void setValidityEnd(LocalDate validityEnd) {
        this.validityEnd = validityEnd;
    }

}
