package com.cognizant.healthgov.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "health_profiles")
@Data
public class HealthProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long healthProfileId;

    private String medicalHistoryNote;
    private String allergies;

    @Enumerated(EnumType.STRING)
    private HealthProfileStatus status;

    @OneToOne
    @JoinColumn(name = "citizen_id")
    private Citizen citizen;
}