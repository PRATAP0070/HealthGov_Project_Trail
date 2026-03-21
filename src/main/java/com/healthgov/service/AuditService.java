package com.healthgov.service;

import java.time.LocalDate;
import java.util.List;

import com.healthgov.model.Audit;

public interface AuditService {

	List<Audit> getAllForOfficer(Long officerId);
	
	Audit getLatestForOfficerOrThrow(Long officerId);

	Audit scheduleAudit(Long officerId, String scope, LocalDate date);

	Audit recordFindings(Long auditId, String findings, String newStatus);

	Audit deleteAudit(Long auditId);

}
