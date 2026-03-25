package com.cognizant.healthgov.model;

import lombok.Data;

@Data
public class MedicalDetails {
    private String bloodGroup;
    private Double weightKg;
    private Double heightCm;
    private String chronicCondition;
    private String lastCheckupDate;
    private Boolean isVaccinated;
}