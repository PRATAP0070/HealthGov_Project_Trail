package com.healthgov.service;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthgov.dto.AuditLogDto;
import com.healthgov.model.AuditLog;
import com.healthgov.repository.AuditLogRepo;
import com.healthgov.repository.RegistrationRepo;
import java.time.LocalDateTime;


@Service
public class AuditLogServiceImpl implements AuditLogService {

	@Autowired
	private AuditLogRepo auditLogRepo;
	
	@Autowired
	private RegistrationRepo registrationRepo;

	@Override
	public void createAuditLog(AuditLogDto dto,String status,String resource) {
		// TODO Auto-generated method stub
		
		AuditLog auditLog = new AuditLog();
		auditLog.setUser(registrationRepo.findById(dto.getUserId()).get());
		auditLog.setResource(resource);
		auditLog.setAction(status);
		auditLog.setTimestamp(LocalDateTime.now());
		
		auditLogRepo.save(auditLog);
	}


    @Override
    public List<AuditLog> getAuditLogsByUser(Long userId) {
        return auditLogRepo.findByUserUserId(userId);
    }
	
	

}
