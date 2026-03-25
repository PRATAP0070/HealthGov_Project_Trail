package com.cognizant.healthgov.dto;

import java.time.LocalDate;

public record CitizenResponseDTO(
	    Long citizenId,
	    String name,
	    LocalDate dob,
	    String gender,
	    String address,
	    String status
	) {}
