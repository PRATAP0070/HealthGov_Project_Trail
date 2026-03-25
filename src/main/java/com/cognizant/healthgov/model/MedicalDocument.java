package com.cognizant.healthgov.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medical_documents")
@Data
@NoArgsConstructor
public class MedicalDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    private String documentName;
    
    @Enumerated(EnumType.STRING)
    private DocumentType documentType; 

    private String fileUrl; 
    private LocalDateTime uploadedAt;

    @ManyToOne
    @JoinColumn(name = "citizen_id")
    private Citizen citizen; 
}