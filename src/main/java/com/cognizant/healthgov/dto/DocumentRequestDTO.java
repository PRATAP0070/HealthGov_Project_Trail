package com.cognizant.healthgov.dto;

import jakarta.validation.constraints.NotBlank;

public record DocumentRequestDTO(
    @NotBlank(message = "Document name is required")
    String documentName,

    @NotBlank(message = "Document type is required")
    String documentType,

    @NotBlank(message = "File URL is required")
    String fileUrl
) {}