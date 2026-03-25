package com.cognizant.healthgov.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class HealthProfileRequestDTO {
    
    @NotBlank(message = "Medical history note is mandatory")
    @Size(max = 2000, message = "Notes cannot exceed 2000 characters")
    private String medicalHistoryNote;

    @NotBlank(message = "Allergies info is required (Type 'None' if none)")
    private String allergies;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "ACTIVE|INACTIVE", message = "Status must be either 'ACTIVE' or 'INACTIVE'")
    private String status;
}