package com.healthgov.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.healthgov.dto.UserDto;
import com.healthgov.exceptions.UserException;
import com.healthgov.model.User;
import com.healthgov.repository.RegistrationRepo;

@Service
public class RegistrationServiceImpl implements RegistrationService {
	
	@Autowired
	private RegistrationRepo userRepo;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDto registerUser(UserDto userDto) throws UserException {
		// TODO Auto-generated method stub
		User user = new User();
		user.setName(userDto.getName());
		user.setRole(userDto.getRole());
		user.setEmail(userDto.getEmail());
		user.setPhone(userDto.getPhone());
		user.setPassword(bcryptEncoder.encode(userDto.getPassword()));
		user.setStatus("Active");
		
		userRepo.save(user);
		
		userDto.setUserId(user.getUserId());
		return userDto;
	}

}
