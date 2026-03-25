package com.cognizant.healthgov.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.healthgov.model.MedicalDocument;
import com.cognizant.healthgov.model.Citizen;
import com.cognizant.healthgov.model.DocumentType;

@Repository
public interface DocumentRepository extends JpaRepository<MedicalDocument, Long> {
    
    List<MedicalDocument> findByCitizen(Citizen citizen);

    boolean existsByCitizenAndDocumentType(Citizen citizen, DocumentType type);
}