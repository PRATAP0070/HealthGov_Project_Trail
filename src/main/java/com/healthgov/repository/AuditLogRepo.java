package com.healthgov.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthgov.model.AuditLog;

public interface AuditLogRepo extends JpaRepository<AuditLog, Long> {

	List<AuditLog> findByUserUserId(Long userId);
}
