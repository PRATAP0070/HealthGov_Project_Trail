package com.healthgov.dto;

import com.healthgov.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import lombok.Data;

@Data
public class UserDto {

	private Long userId;
	private String name;
	@NotNull(message = "User role is required")
	@Enumerated(EnumType.STRING)
	private UserRole role;
	private String email;
	private String phone;
	private String status;
	private String password;
	

	
}