package com.healthgov.model;

import java.time.LocalDate;

import com.healthgov.enums.GrantStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Grants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long grantId;
 
    @ManyToOne
    @JoinColumn(name = "projectId")
    private ResearchProject project;
 
    @ManyToOne
    @JoinColumn(name = "researcherId")
    private Users researcher;
 
    private Double amount;
    private LocalDate date;
    
    @Enumerated(EnumType.STRING)
    private GrantStatus status;
}