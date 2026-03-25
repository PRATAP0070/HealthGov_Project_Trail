package com.cognizant.healthgov.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public record CitizenRequestDTO(
	    @NotBlank(message = "Full name is required")
	    String name,
	    
	    @NotNull(message = "Date of Birth is required")
	    @Past(message = "DOB must be a past date")
	    LocalDate dob,
	    
	    @NotBlank(message = "Gender is required")
	    String gender,
	    
	    String address,
	    
	    @Email(message = "Valid contact email is required")
	    String contactInfo
	) {}
