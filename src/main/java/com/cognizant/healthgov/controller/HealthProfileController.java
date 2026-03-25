package com.cognizant.healthgov.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.healthgov.dto.HealthProfileResponseDTO;
import com.cognizant.healthgov.model.HealthProfile;
import com.cognizant.healthgov.service.HealthProfileService;

@RestController
@RequestMapping("/health-profiles")
public class HealthProfileController {

	private final HealthProfileService service;

	public HealthProfileController(HealthProfileService service) {
	    this.service = service;
	}
	
    @PostMapping("/{citizenId}")
    public ResponseEntity<HealthProfileResponseDTO> create(@PathVariable Long citizenId, @RequestBody HealthProfile profile) {
        return ResponseEntity.ok(service.saveOrUpdateProfile(citizenId, profile));
    }

    @GetMapping("/{citizenId}")
    public ResponseEntity<HealthProfileResponseDTO> get(@PathVariable Long citizenId) {
        return ResponseEntity.ok(service.getProfile(citizenId));
    }
    
    @PutMapping("/{citizenId}")
    public ResponseEntity<HealthProfileResponseDTO> update(@PathVariable Long citizenId, @RequestBody HealthProfile profile) {
        return ResponseEntity.ok(service.saveOrUpdateProfile(citizenId, profile));
    }

    @DeleteMapping("/{citizenId}")
    public ResponseEntity<String> delete(@PathVariable Long citizenId) {
        service.deleteProfile(citizenId);
        return ResponseEntity.ok("Health Profile for Citizen ID " + citizenId + " has been deleted successfully.");
    }
}