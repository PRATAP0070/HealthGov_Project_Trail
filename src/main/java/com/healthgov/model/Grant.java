package com.healthgov.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "grants")
public class Grant {
    @Id
    private Long grantId;
 
    @ManyToOne
    @JoinColumn(name = "projectId")
    private ResearchProject project;
 
    @ManyToOne
    @JoinColumn(name = "researcherId")
    private User researcher;
 
    private Double amount;
    private Date date;
    private String status;
}