package com.healthgov.model;

import com.healthgov.enums.GrantStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GrantApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "researcher_id", nullable = false)
    private Users researcher;

    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private ResearchProject project;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GrantStatus status;  

    @Column(name = "submitted_date", nullable = false)
    private LocalDate submittedDate; 
}