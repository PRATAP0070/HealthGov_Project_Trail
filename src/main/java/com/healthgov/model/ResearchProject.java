package com.healthgov.model;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class ResearchProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;
    private String title;
    private String description;
 
    @ManyToOne
    @JoinColumn(name = "researcherId")
    private Users researcher;
 
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
 
    @OneToMany(mappedBy = "project")
    private List<GrantApplication> grantApplications;
 
    @OneToMany(mappedBy = "project")
    private List<Grants> grants;
}
