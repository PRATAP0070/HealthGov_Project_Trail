package com.healthgov.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class GrantApplication {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long applicationId;

	@ManyToOne
	@JoinColumn(name = "researcherId")
	private Users researcher;

	@ManyToOne
	@JoinColumn(name = "projectId")
	private ResearchProject project;

	private LocalDate submittedDate;
	private String status;
}