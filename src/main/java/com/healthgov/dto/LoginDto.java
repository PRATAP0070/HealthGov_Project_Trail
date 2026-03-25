package com.healthgov.dto;


import com.healthgov.model.Role;

import lombok.Data;

@Data
public class LoginDto {

	private String name;
	private String password;
	private Role role;
}