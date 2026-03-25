package com.healthgov.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.healthgov.dto.InfrastructureCreateRequest;
import com.healthgov.dto.InfrastructureResponse;
import com.healthgov.dto.InfrastructureUpdateRequest;
import com.healthgov.model.InfrastructureStatus;
import com.healthgov.model.InfrastructureType;
import com.healthgov.service.InfrastructureService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/infrastructures")
@Slf4j // Logger log = LoggerFactory.getLogger(InfrastructureController.class);
public class InfrastructureController {

	private final InfrastructureService service;

	public InfrastructureController(InfrastructureService service) {
		this.service = service;
	}

	@PostMapping("/save") // http://localhost:9090/infrastructures/save
	public InfrastructureResponse create(@Valid @RequestBody InfrastructureCreateRequest request) {
		log.info("Creating new infrastructure");
		return service.createInfrastructure(request);
	}

	@PutMapping("/update/{infraId}") // http://localhost:9090/infrastructures/update/1
	public InfrastructureResponse update(@PathVariable Long infraId,
			@Valid @RequestBody InfrastructureUpdateRequest request) {
		log.info("Updating infrastructure with id={}", infraId);
		return service.updateInfrastructure(infraId, request);
	}

	@DeleteMapping("/delete/{infraId}") // http://localhost:9090/infrastructures/delete/1
	public String delete(@PathVariable Long infraId) {
		log.info("Deleting infrastructure with id={}", infraId);
		service.deleteInfrastructureById(infraId);
		return "Infrastructure deleted successfully";
	}

	@GetMapping("/getById/{infraId}") // http://localhost:9090/infrastructures/getById/1
	public InfrastructureResponse getById(@PathVariable Long infraId) {
		log.info("Fetching infrastructure by id={}", infraId);
		return service.getInfrastructureById(infraId);
	}

	@GetMapping("/getAll") // http://localhost:9090/infrastructures/getAll
	public List<InfrastructureResponse> getAll() {
		log.info("Fetching all infrastructures");
		return service.getAllInfrastructures();
	}

	@GetMapping("/program/{programId}") // http://localhost:9090/infrastructures/program/1
	public List<InfrastructureResponse> getByProgram(@PathVariable Long programId) {
		log.info("Fetching infrastructures for programId={}", programId);
		return service.getInfrastructuresByProgramId(programId);
	}

	@GetMapping("/search")
	public List<InfrastructureResponse> search(@RequestParam InfrastructureType type, @RequestParam String location,
			@RequestParam InfrastructureStatus status)
//	http://localhost:9090/infrastructures/search?location=Chennai&type=HOSPITAL&status=OPERATIONAL
	{
		log.info("Searching infrastructures with type={}, location={}, status={}", type, location, status);
		return service.getInfrastructuresByTypeLocationAndStatus(type, location.trim(), status);
	}
}