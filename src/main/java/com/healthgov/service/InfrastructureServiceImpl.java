package com.healthgov.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.healthgov.dto.InfrastructureCreateRequest;
import com.healthgov.dto.InfrastructureResponse;
import com.healthgov.dto.InfrastructureUpdateRequest;
import com.healthgov.exceptions.ResourceNotFoundException;
import com.healthgov.model.HealthProgram;
import com.healthgov.model.Infrastructure;
import com.healthgov.model.InfrastructureStatus;
import com.healthgov.model.InfrastructureType;
import com.healthgov.repository.HealthProgramRepository;
import com.healthgov.repository.InfrastructureRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class InfrastructureServiceImpl implements InfrastructureService {

	private final InfrastructureRepository infraRepo;
	private final HealthProgramRepository programRepo;

	public InfrastructureServiceImpl(InfrastructureRepository infraRepo, HealthProgramRepository programRepo) {
		this.infraRepo = infraRepo;
		this.programRepo = programRepo;
	}

	@Override
	public InfrastructureResponse createInfrastructure(InfrastructureCreateRequest request) {
		// Load program
		log.info("Creating infrastructure for programId={}", request.getProgramId());

		HealthProgram program = programRepo.findById(request.getProgramId()).orElseThrow(() -> {
			log.error("HealthProgram not found with id={}", request.getProgramId());
			return new ResourceNotFoundException("HealthProgram not found: " + request.getProgramId());
		});

		// Build and save entity
		Infrastructure entity = new Infrastructure();
		entity.setProgram(program);
		entity.setType(request.getType());
		entity.setLocation(request.getLocation());
		entity.setCapacity(request.getCapacity());
		entity.setStatus(request.getStatus());

		entity = infraRepo.save(entity);

		log.info("Infrastructure created successfully with infraId={}", entity.getInfraId());

		// Map to response
		return toResponse(entity);
	}

	@Override
	public InfrastructureResponse updateInfrastructure(Long infraId, InfrastructureUpdateRequest request) {

		log.info("Updating infrastructure with infraId={}", infraId);

		Infrastructure entity = getInfrastructureOrThrow(infraId);

		entity.setType(request.getType());
		entity.setLocation(request.getLocation());
		entity.setCapacity(request.getCapacity());
		entity.setStatus(request.getStatus());

		infraRepo.save(entity);
		log.info("Infrastructure updated successfully with infraId={}", infraId);
		return toResponse(entity);
	}

	@Override
	public void deleteInfrastructureById(Long infraId) {

		log.info("Deleting infrastructure with infraId={}", infraId);

		Infrastructure entity = getInfrastructureOrThrow(infraId);

		infraRepo.delete(entity);
		log.info("Infrastructure deleted successfully with infraId={}", infraId);

	}

	@Override
	@Transactional(readOnly = true)
	public InfrastructureResponse getInfrastructureById(Long infraId) {

		log.info("Fetching infrastructure with infraId={}", infraId);

		Infrastructure entity = getInfrastructureOrThrow(infraId);
		return toResponse(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public List<InfrastructureResponse> getAllInfrastructures() {
		log.info("Fetching all infrastructures");
		return infraRepo.findAll().stream().map(this::toResponse).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<InfrastructureResponse> getInfrastructuresByProgramId(Long programId) {
		log.info("Fetching infrastructures for programId={}", programId);
		return infraRepo.findByProgram_ProgramId(programId).stream().map(this::toResponse).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<InfrastructureResponse> getInfrastructuresByTypeLocationAndStatus(InfrastructureType type,
			String location, InfrastructureStatus status) {

		log.info("Searching infrastructures with type={}, location={}, status={}", type, location, status);

		return infraRepo.findByTypeAndLocationAndStatus(type, location, status).stream().map(this::toResponse).toList();
	}

	private Infrastructure getInfrastructureOrThrow(Long infraId) {
		log.debug("Loading infrastructure with infraId={}", infraId);

		return infraRepo.findById(infraId).orElseThrow(() -> {
			log.error("Infrastructure not found with infraId={}", infraId);
			return new ResourceNotFoundException("Infrastructure not found: " + infraId);
		});
	}

	private InfrastructureResponse toResponse(Infrastructure e) {
		InfrastructureResponse dto = new InfrastructureResponse();
		dto.setInfraId(e.getInfraId());
		if (e.getProgram() != null) {
			dto.setProgramId(e.getProgram().getProgramId());
			dto.setProgramTitle(e.getProgram().getTitle());
		}
		dto.setType(e.getType());
		dto.setLocation(e.getLocation());
		dto.setCapacity(e.getCapacity());
		dto.setStatus(e.getStatus());
		return dto;
	}

}