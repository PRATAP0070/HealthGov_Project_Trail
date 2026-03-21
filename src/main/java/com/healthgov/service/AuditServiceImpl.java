package com.healthgov.service;

import java.time.LocalDate;
import java.util.List;

import com.healthgov.model.Audit;

public class AuditServiceImpl implements AuditService {

	@Override
	public List<Audit> getAllForOfficer(Long officerId) {
		return null;
	}

	@Override
	public Audit getLatestForOfficerOrThrow(Long officerId) {
		return null;
	}

	@Override
	public Audit scheduleAudit(Long officerId, String scope, LocalDate date) {
		return null;
	}

	@Override
	public Audit recordFindings(Long auditId, String findings, String newStatus) {
		return null;
	}

	@Override
	public Audit deleteAudit(Long auditId) {
		return null;
	}

	
}
