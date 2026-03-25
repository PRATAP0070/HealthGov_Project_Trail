package com.healthgov.service;

import com.healthgov.dto.UserDto;
import com.healthgov.exceptions.UserException;

public interface RegistrationService {

	public UserDto registerUser(UserDto userDto) throws UserException;
}
