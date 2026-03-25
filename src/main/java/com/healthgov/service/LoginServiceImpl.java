package com.healthgov.service;

import java.util.ArrayList;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.healthgov.dto.AuditLogDto;

import com.healthgov.model.User;
import com.healthgov.repository.LoginRepo;



@Service
public class LoginServiceImpl implements UserDetailsService {

	@Autowired
	private LoginRepo loginRepo;

	
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

		User user = loginRepo.findByName(name);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + name);
		}
		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
				new ArrayList<>());
		
	}
	
	public AuditLogDto getUserById(String name) {
		User user = loginRepo.findByName(name);
		AuditLogDto auditLogDto = new AuditLogDto();
		auditLogDto.setUserId(user.getUserId());
		return auditLogDto;
	}
	

}
