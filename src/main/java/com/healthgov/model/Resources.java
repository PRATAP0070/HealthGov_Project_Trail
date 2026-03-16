package com.healthgov.model;

import com.healthgov.enums.ResourceType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Resources {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long resourceId;

	@ManyToOne
	@JoinColumn(name = "programId")
	private HealthProgram program;

	@Enumerated(EnumType.STRING)
    private ResourceType type;
	private Integer quantity;
	private String status;
}