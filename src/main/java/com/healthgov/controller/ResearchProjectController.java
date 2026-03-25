package com.healthgov.controller;

import com.healthgov.dto.ResearchProjectCreateRequest;
import com.healthgov.dto.ResearchProjectUpdateRequest;
import com.healthgov.dto.ResearchProjectResponse;
import com.healthgov.service.ResearchProjectService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/research")
@RequiredArgsConstructor
public class ResearchProjectController {

    private final ResearchProjectService service;

   //Create project
    @PostMapping("/createProject")
    public ResponseEntity<List<String>> create(
            @Valid @RequestBody ResearchProjectCreateRequest req) {

        log.info("Received CREATE request for researcherId={}", req.getResearcherId());

        ResearchProjectResponse created = service.create(req);

        log.info("Project created successfully with projectId={}", created.getProjectId());

        return ResponseEntity.ok(
                List.of(
                        "Project created successfully.",
                        "Project ID: " + created.getProjectId()
                )
        );
    }

    //Update project
    @PutMapping("/updateProject")
    public ResponseEntity<List<String>> update(
            @Valid @RequestBody ResearchProjectUpdateRequest req) {

        log.info("Received UPDATE request for projectId={}", req.getProjectId());

        service.update(req);

        log.info("Project updated successfully: projectId={}", req.getProjectId());

        return ResponseEntity.ok(
                List.of("Project updated successfully.")
        );
    }

   //List projects
    @GetMapping("/projects")
    public List<ResearchProjectResponse> list(
            @RequestParam(required = false) String status) {

        log.info("Fetching project list with status={}", status);

        List<ResearchProjectResponse> result = service.list(status);

        log.info("Projects fetched: count={}, filterStatus={}", result.size(), status);

        return result;
    }

   //Get project by id
    @GetMapping("/projects/{id}")
    public ResearchProjectResponse get(@PathVariable Long id) {

        log.info("Fetching project by ID={}", id);

        ResearchProjectResponse response = service.get(id);

        log.info("Project fetched successfully: projectId={}", id);

        return response;
    }

   //Delete project
    @DeleteMapping("/projects/{id}")
    public ResponseEntity<List<String>> delete(@PathVariable Long id) {

        log.warn("DELETE request received for projectId={}", id);

        service.delete(id);

        log.info("Project deleted successfully: projectId={}", id);

        return ResponseEntity.ok(
                List.of("Project deleted successfully.")
        );
    }
}