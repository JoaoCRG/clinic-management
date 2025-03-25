package com.ClinicManagement.DTOs;

import com.ClinicManagement.Entities.Lot;

public class LotDTO {
    private Long id;
    private String brand;
    private String reference;
    private String lotNumber;

    public LotDTO(Lot lot) {
        this.id = lot.getId();
        this.brand = lot.getBrand();
        this.reference = lot.getReference();
        this.lotNumber = lot.getLotNumber();
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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }
}
