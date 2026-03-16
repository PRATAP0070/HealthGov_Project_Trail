package com.healthgov.model;

import java.time.LocalDate;

import com.healthgov.enums.ComplianceType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ComplianceRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long complianceId;
	private Long entityId;

	@Enumerated(EnumType.STRING)
	private ComplianceType type;

	private String result;
	private LocalDate date;
	private String notes;
}