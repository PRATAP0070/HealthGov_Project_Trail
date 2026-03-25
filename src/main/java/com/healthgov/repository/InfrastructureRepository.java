package com.healthgov.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthgov.model.Infrastructure;
import com.healthgov.model.InfrastructureStatus;
import com.healthgov.model.InfrastructureType;

public interface InfrastructureRepository extends JpaRepository<Infrastructure, Long> {

	List<Infrastructure> findByProgram_ProgramId(Long programId);

	List<Infrastructure> findByTypeAndLocationAndStatus(InfrastructureType type, String location,
			InfrastructureStatus status);
}