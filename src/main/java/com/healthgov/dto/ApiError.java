package com.healthgov.dto;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

	Instant timestamp;
	int status;
	String error;
	String code;
	String message;
	String path;

}
