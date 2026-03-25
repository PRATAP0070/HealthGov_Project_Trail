package com.cognizant.healthgov.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cognizant.healthgov.dto.DocumentRequestDTO;
import com.cognizant.healthgov.dto.DocumentResponseDTO;
import com.cognizant.healthgov.service.DocumentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/documents")
public class DocumentController {

	private final DocumentService service;

	public DocumentController(DocumentService service) {
	    this.service = service;
	}
    
    @PostMapping("/{citizenId}")
    public ResponseEntity<String> upload(
            @PathVariable Long citizenId, 
            @Valid @RequestBody DocumentRequestDTO request) {
        
        service.uploadDocument(citizenId, request);
        
        return ResponseEntity.ok("Documents posted successfully for Citizen ID: " + citizenId);
    }
    
    @GetMapping("/citizen/{citizenId}")
    public ResponseEntity<List<DocumentResponseDTO>> getAll(@PathVariable Long citizenId) {
        return ResponseEntity.ok(service.getCitizenDocuments(citizenId));
    }
}