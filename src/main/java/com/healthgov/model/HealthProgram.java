package com.healthgov.model;

import java.sql.Date;
import java.util.List;

import com.healthgov.enums.ProgramStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Data;

@Entity
@Data
@Data
public class HealthProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long programId;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Double budget;
    @Enumerated(EnumType.STRING)
    private ProgramStatus status;
 
    @OneToMany(mappedBy = "program")
    private List<Enrollment> enrollments;
 
    @OneToMany(mappedBy = "program")
    private List<Resources> resources;
 
    @OneToMany(mappedBy = "program")
    private List<Infrastructure> infrastructures;
    
    
}

