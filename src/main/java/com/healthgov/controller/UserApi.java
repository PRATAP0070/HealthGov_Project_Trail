package com.healthgov.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import com.healthgov.config.JwtTokenUtil;
import com.healthgov.dto.ForgetPasswordDto;
import com.healthgov.dto.JwtResponse;
import com.healthgov.dto.LoginDto;
import com.healthgov.dto.UserDto;
import com.healthgov.exceptions.UserException;

import com.healthgov.service.AuditLogService;
import com.healthgov.service.ForgetPasswordService;
import com.healthgov.service.LoginServiceImpl;
import com.healthgov.service.RegistrationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/healthGov")
@Slf4j
public class UserApi {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private LoginServiceImpl loginService;

	@Autowired
	private RegistrationService userService;

	@Autowired
	private AuditLogService auditLogService;

	@Autowired
	private ForgetPasswordService forgetPasswordService;

	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) throws UserException {
		// User createdId = userService.registerUser(userDto);
//		String successMsg = "User Id Created with "+createdId;
		UserDto savedDto = userService.registerUser(userDto);
		return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginDto loginDto) throws Exception {
		log.info("Inside Login method " + loginDto);
		authenticate(loginDto.getName(), loginDto.getPassword());
		log.info("Inside Login method 1" + loginDto);
		final UserDetails userDetails = loginService.loadUserByUsername(loginDto.getName());
		log.info("Inside Login method 2" + loginDto);

		final String token = jwtTokenUtil.generateToken(userDetails, loginDto.getRole());
		auditLogService.createAuditLog(loginService.getUserById(loginDto.getName()), "login", "Profile");
		log.info("Inside Login method 3 " + loginDto);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	
	@PutMapping("/forgotPassword")
	public ResponseEntity<String> forgotPassword(@RequestBody ForgetPasswordDto dto) {
		return new ResponseEntity<>(forgetPasswordService.resetPassword(dto),HttpStatus.CREATED);
	}
	
	private void authenticate(String name, String password) throws Exception {
		try {
			log.info("Inside Login method " + name + " " + password);
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(name, password));
		} catch (DisabledException e) {
			log.info("Inside Login method 1" + name + " " + password);

			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			log.info("Inside Login method 2" + name + " " + password);
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}
