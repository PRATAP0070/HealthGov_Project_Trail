package com.healthgov.model;

import java.time.LocalDate;

import com.healthgov.enums.ComplianceResult;
import com.healthgov.enums.ComplianceType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "compliance_record", uniqueConstraints = {
	@UniqueConstraint(name = "uq_compliance_type_entity", columnNames = { "type", "entity_id" }) }, indexes = {
				@Index(name = "ix_compliance_record_entity", columnList = "entity_id"),
				@Index(name = "ix_compliance_record_type", columnList = "type"),
				@Index(name = "ix_compliance_record_date", columnList = "date") })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long complianceId;

	@NotNull
	@Column(name = "entity_id", nullable = false)
	private Long entityId;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ComplianceType type;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private ComplianceResult result;

	@NotNull
	@Column(nullable = false)
	private LocalDate date;

	@NotBlank
	@Column(nullable = false, length = 2000)
	private String notes;
}