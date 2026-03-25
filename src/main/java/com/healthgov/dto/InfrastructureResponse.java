package com.healthgov.dto;

import com.healthgov.model.InfrastructureStatus;
import com.healthgov.model.InfrastructureType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InfrastructureResponse {

	private Long infraId;
	private Long programId;
	private String programTitle;
	private InfrastructureType type;
	private String location;
	private int capacity;
	private InfrastructureStatus status;

}
