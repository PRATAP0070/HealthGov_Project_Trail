package com.healthgov.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity

// same here are created  the indexes for the polymorphic behaviour
@Table(name = "audit", indexes = { @Index(name = "ix_audit_officer_id", columnList = "officer_id"),
		@Index(name = "ix_audit_status", columnList = "status"), @Index(name = "ix_audit_date", columnList = "date") })

@Data
public class Audit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long auditId;

	// many audits can be linked to single compliance officer
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "officer_id",nullable=false)
	private Users officer;

	private String scope;

	private String findings;
	private LocalDate date;
	private String status;
}
