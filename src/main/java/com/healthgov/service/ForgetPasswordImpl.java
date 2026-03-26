package com.healthgov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.healthgov.dto.ForgetPasswordDto;
import com.healthgov.model.Users;
import com.healthgov.repository.LoginRepo;
import com.healthgov.repository.RegistrationRepo;

@Service
public class ForgetPasswordImpl implements ForgetPasswordService {

	@Autowired
	private RegistrationRepo registrationRepo;
	
	@Autowired
	private LoginRepo loginRepo;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public String resetPassword(ForgetPasswordDto dto) {
		
		Users user = loginRepo.findByName(dto.getName());

		if (user == null) {
			throw new RuntimeException("User not found with email: " + dto.getName());
		}

		user.setPassword(bcryptEncoder.encode(dto.getPassword()));
		registrationRepo.save(user);

		return "Password updated successfully!";
	}

}
