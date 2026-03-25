package com.cognizant.healthgov.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.healthgov.dto.DocumentRequestDTO;
import com.cognizant.healthgov.dto.DocumentResponseDTO;
import com.cognizant.healthgov.exceptions.CitizenNotFoundException;
import com.cognizant.healthgov.model.Citizen;
import com.cognizant.healthgov.model.DocumentType;
import com.cognizant.healthgov.model.MedicalDocument;
import com.cognizant.healthgov.repository.CitizenRepository;
import com.cognizant.healthgov.repository.DocumentRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepo;
    private final CitizenRepository citizenRepo;

    @Override
    public String uploadDocument(Long citizenId, DocumentRequestDTO request) {
        Optional<Citizen> opt = citizenRepo.findById(citizenId);
        if (opt.isEmpty()) {
            throw new CitizenNotFoundException(citizenId);
        }
        Citizen citizen = opt.get();

        DocumentType type = DocumentType.valueOf(request.documentType().toUpperCase());
        
        if (documentRepo.existsByCitizenAndDocumentType(citizen, type)) {
            throw new RuntimeException("Document of type " + type + " already exists for this citizen!");
        }

        MedicalDocument doc = new MedicalDocument();
        doc.setCitizen(citizen);
        doc.setDocumentName(request.documentName());
        doc.setDocumentType(type);
        doc.setFileUrl(request.fileUrl());
        doc.setUploadedAt(LocalDateTime.now());

        documentRepo.save(doc);
        return "Documents posted successfully for Citizen ID: " + citizenId;
    }

    @Override
    public List<DocumentResponseDTO> getCitizenDocuments(Long citizenId) {
        Optional<Citizen> optCitizen = citizenRepo.findById(citizenId);
        if (optCitizen.isEmpty()) {
            throw new CitizenNotFoundException(citizenId);
        }

        List<MedicalDocument> docs = documentRepo.findByCitizen(optCitizen.get());
        List<DocumentResponseDTO> dtos = new ArrayList<>();
        
        for (MedicalDocument d : docs) {
            dtos.add(mapToDTO(d));
        }
        return dtos;
    }

    private DocumentResponseDTO mapToDTO(MedicalDocument d) {
        String typeStr = (d.getDocumentType() != null) ? d.getDocumentType().name() : "";
        
        return new DocumentResponseDTO(
            d.getDocumentId(),
            d.getDocumentName(),
            typeStr,
            d.getFileUrl(),
            d.getUploadedAt(),
            d.getCitizen().getCitizenId()
        );
    }
}