package com.cognizant.healthgov.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "citizens")
@Data
public class Citizen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long citizenId; 
    private String name;
    private LocalDate dob; 
    
    @Enumerated(EnumType.STRING)
    private Gender gender; 
    
    private String address;
    private String contactInfo;

    @Enumerated(EnumType.STRING)
    private RegistrationStatus status; 

}
