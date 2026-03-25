package com.healthgov.model;

import java.time.LocalDate;

import com.healthgov.enums.GrantStatus;

<<<<<<< HEAD
import jakarta.persistence.Column;
=======
>>>>>>> origin/Prushottamverma
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
<<<<<<< HEAD
import java.time.LocalDateTime;

import com.healthgov.enums.GrantStatus;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Grants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long grantId;

    @OneToOne(optional = false)
    @JoinColumn(name = "project_id", nullable = false, unique = true)
    private ResearchProject project;

    @ManyToOne(optional = false)
    @JoinColumn(name = "researcher_id", nullable = false)
    private Users researcher;

    private Double amount;   

    @Column(name = "granted_at", nullable = false)
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private GrantStatus status; 
}
=======
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
>>>>>>> origin/Prushottamverma
