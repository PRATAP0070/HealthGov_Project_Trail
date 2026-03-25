package com.healthgov.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.healthgov.dto.ResourceCreateRequest;
import com.healthgov.dto.ResourceResponse;
import com.healthgov.dto.ResourceUpdateRequest;
import com.healthgov.exceptions.ResourceNotFoundException;
import com.healthgov.model.HealthProgram;
import com.healthgov.model.Resource;
import com.healthgov.model.ResourceStatus;
import com.healthgov.model.ResourceType;
import com.healthgov.repository.HealthProgramRepository;
import com.healthgov.repository.ResourceRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ResourceServiceImpl implements ResourceService {

	private final ResourceRepository resourceRepo;
	private final HealthProgramRepository programRepo;

	public ResourceServiceImpl(ResourceRepository resourceRepo, HealthProgramRepository programRepo) {
		this.resourceRepo = resourceRepo;
		this.programRepo = programRepo;
	}

	@Override
	public ResourceResponse createResource(ResourceCreateRequest request) {

		log.info("Creating resource for programId={}", request.getProgramId());

		HealthProgram program = programRepo.findById(request.getProgramId()).orElseThrow(() -> {
			log.error("HealthProgram not found with programId={}", request.getProgramId());
			return new ResourceNotFoundException("HealthProgram not found: " + request.getProgramId());
		});

		// Build and save entity
		Resource entity = new Resource();
		entity.setProgram(program);
		entity.setType(request.getType());
		entity.setQuantity(request.getQuantity());
		entity.setStatus(request.getStatus());

		entity = resourceRepo.save(entity);

		log.info("Resource created successfully with resourceId={}", entity.getResourceId());

		// Map to response
		return toResponse(entity);
	}

	@Override
	public ResourceResponse updateResource(Long resourceId, ResourceUpdateRequest request) {

		log.info("Updating resource with resourceId={}", resourceId);

		Resource entity = getResourceOrThrow(resourceId);

		entity.setType(request.getType());
		entity.setQuantity(request.getQuantity());
		entity.setStatus(request.getStatus());

		resourceRepo.save(entity);
		log.info("Resource updated successfully with resourceId={}", resourceId);
		return toResponse(entity);
	}

	@Override
	public void deleteResourceById(Long resourceId) {

		log.info("Deleting resource with resourceId={}", resourceId);

		Resource entity = getResourceOrThrow(resourceId);
		resourceRepo.delete(entity);

		log.info("Resource deleted successfully with resourceId={}", resourceId);

	}

	@Override
	@Transactional(readOnly = true)
	public ResourceResponse getResourceById(Long resourceId) {

		log.info("Fetching resource with resourceId={}", resourceId);

		Resource entity = getResourceOrThrow(resourceId);
		return toResponse(entity);

	}

	@Override
	@Transactional(readOnly = true)
	public List<ResourceResponse> getAllResources() {
		log.info("Fetching all resources");
		return resourceRepo.findAll().stream().map(this::toResponse).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ResourceResponse> getResourcesByProgramId(Long programId) {
		log.info("Fetching resources for programId={}", programId);
		return resourceRepo.findByProgram_ProgramId(programId).stream().map(this::toResponse).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ResourceResponse> getResourcesByTypeAndStatus(ResourceType type, ResourceStatus status) {
		log.info("Searching resources with type={} and status={}", type, status);
		return resourceRepo.findByTypeAndStatus(type, status).stream().map(this::toResponse).toList();
	}

	private Resource getResourceOrThrow(Long resourceId) {

		log.debug("Loading resource with resourceId={}", resourceId);

		return resourceRepo.findById(resourceId).orElseThrow(() -> {
			log.error("Resource not found with resourceId={}", resourceId);
			return new ResourceNotFoundException("Resource not found: " + resourceId);
		});
	}

	private ResourceResponse toResponse(Resource e) {
		ResourceResponse dto = new ResourceResponse();
		dto.setResourceId(e.getResourceId());
		if (e.getProgram() != null) {
			dto.setProgramId(e.getProgram().getProgramId());
			dto.setProgramTitle(e.getProgram().getTitle());
		}
		dto.setType(e.getType());
		dto.setQuantity(e.getQuantity());
		dto.setStatus(e.getStatus());
		return dto;
	}
}
