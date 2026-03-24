package com.healthgov.service;

import java.util.List;

import com.healthgov.dto.AuditLogDto;
import com.healthgov.model.AuditLog;

public interface AuditLogService {
    void createAuditLog(AuditLogDto dto,String status,String resource);
    List<AuditLog> getAuditLogsByUser(Long userId);
}