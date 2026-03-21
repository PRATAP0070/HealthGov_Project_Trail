package com.healthgov.service;

import java.util.List;

import com.healthgov.dto.ComplianceCreateRequest;
import com.healthgov.dto.ComplianceUpdateRequest;
import com.healthgov.enums.ComplianceType;
import com.healthgov.model.ComplianceRecord;

public interface ComplianceService {

	List<ComplianceRecord> getAllComplianceRecords();

	ComplianceRecord getOneByEntityIdAndType(ComplianceType type, Long entityId);

	ComplianceRecord createRecord(ComplianceCreateRequest complianceRecord);

	ComplianceRecord updateExisting(ComplianceType type, Long entityId, ComplianceUpdateRequest dto);

	ComplianceRecord updateResultByEntityIdAndType(ComplianceType type, Long entityId, String result);

	ComplianceRecord updateNotesByEntityIdAndType(ComplianceType type, Long entityId, String notes);
	
	ComplianceRecord deleteById(Long Id);
}