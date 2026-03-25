package com.healthgov.model;
import java.time.LocalDate;
import java.util.List;

import com.healthgov.enums.ProjectStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import com.healthgov.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResearchProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "researcherId", nullable = false)
    private Users researcher;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;   

    private String reason;          

    @OneToMany(mappedBy = "project")
    private List<GrantApplication> grantApplications;

    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL)
    private Grants grant;

}
