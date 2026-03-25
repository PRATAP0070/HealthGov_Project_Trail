package com.cognizant.healthgov.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.healthgov.dto.CitizenRequestDTO;
import com.cognizant.healthgov.dto.CitizenResponseDTO;
import com.cognizant.healthgov.service.CitizenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/citizens")
public class CitizenController {

    private final CitizenService service;

    public CitizenController(CitizenService service) {
        this.service = service;
    }
    @PostMapping 
    public ResponseEntity<CitizenResponseDTO> register(@Valid @RequestBody CitizenRequestDTO request) {
        return ResponseEntity.ok(service.registerCitizen(request));
    }


    @GetMapping("/{citizenId}") 
    public ResponseEntity<CitizenResponseDTO> getDetails(@PathVariable Long citizenId) {
        return ResponseEntity.ok(service.getCitizen(citizenId));
    }

    @GetMapping 
    public ResponseEntity<List<CitizenResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAllCitizens());
    }

    @PutMapping("/{citizenId}")
    public ResponseEntity<CitizenResponseDTO> update(@PathVariable Long citizenId, @Valid @RequestBody CitizenRequestDTO request) {
        return ResponseEntity.ok(service.updateCitizen(citizenId, request));
    }

    @DeleteMapping("/{citizenId}")
    public ResponseEntity<String> delete(@PathVariable Long citizenId) {
        service.deleteCitizen(citizenId);
        return ResponseEntity.ok("Citizen deleted successfully with ID: " + citizenId);
    }
}