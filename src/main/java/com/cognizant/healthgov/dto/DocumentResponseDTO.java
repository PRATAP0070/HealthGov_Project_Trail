package com.cognizant.healthgov.dto;

import java.time.LocalDateTime;

public record DocumentResponseDTO(
    Long documentId,
    String documentName,
    String documentType,
    String fileUrl,
    LocalDateTime uploadedAt,
    Long citizenId
) {}