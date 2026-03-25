package com.healthgov.dto;

import com.healthgov.model.ResourceStatus;
import com.healthgov.model.ResourceType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResourceResponse {
	private Long resourceId;
	private Long programId;
	private String programTitle;
	private ResourceType type;
	private Integer quantity;
	private ResourceStatus status;
}
