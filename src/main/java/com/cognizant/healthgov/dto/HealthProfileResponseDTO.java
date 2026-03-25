package com.cognizant.healthgov.dto;

public record HealthProfileResponseDTO(
    Long healthProfileId,
    Long citizenId,
    String medicalHistoryNote,
    String allergies,
    String status
) {}