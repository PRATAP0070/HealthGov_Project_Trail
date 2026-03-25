package com.healthgov.dto;


import com.healthgov.model.Role;

import lombok.Data;

@Data
public class UserDto {

	private Long userId;
	private String name;
	private Role role;
	private String email;
	private String phone;
	private String status;
	private String password;
	

	
}