package com.cognizant.healthgov.service;

import java.util.List;
import com.cognizant.healthgov.dto.DocumentRequestDTO;
import com.cognizant.healthgov.dto.DocumentResponseDTO;

public interface DocumentService {
    String uploadDocument(Long citizenId, DocumentRequestDTO request);
    List<DocumentResponseDTO> getCitizenDocuments(Long citizenId);
}