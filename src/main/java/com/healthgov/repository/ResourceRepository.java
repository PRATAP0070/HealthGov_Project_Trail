package com.healthgov.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthgov.model.Resource;
import com.healthgov.model.ResourceStatus;
import com.healthgov.model.ResourceType;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

	List<Resource> findByProgram_ProgramId(Long programId);

	List<Resource> findByTypeAndStatus(ResourceType type, ResourceStatus status);
}