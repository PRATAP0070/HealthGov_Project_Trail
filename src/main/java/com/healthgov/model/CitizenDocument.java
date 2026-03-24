package com.healthgov.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class CitizenDocument {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long documentId;

	@ManyToOne
	@JoinColumn(name = "citizenId") // recheck
	private Citizen citizen;

	private String docType;
	private String fileURI;
	private Date uploadedDate;
	private String verificationStatus;
}