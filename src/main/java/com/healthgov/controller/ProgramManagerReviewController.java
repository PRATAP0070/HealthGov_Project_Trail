package com.healthgov.controller;

import com.healthgov.dto.ProgramManagerDecisionRequest;
import com.healthgov.dto.ResearchProjectResponse;
import com.healthgov.service.ProgramManagerReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ProgramManagerReviewController {

    private final ProgramManagerReviewService service;

   //List pending projects
    @GetMapping("/projects/pending")
    public List<ResearchProjectResponse> pending() {
        log.info("Fetching all PENDING projects for Program Manager review.");
        return service.listPending();
    }

    //List by status
    @GetMapping("/projects")
    public List<ResearchProjectResponse> list(
            @RequestParam(required = false) String status) {

        log.info("Fetching projects with status={}", status);

        if (status == null || status.isBlank()) {
            log.info("No status provided. Defaulting to PENDING projects.");
            return service.listByStatus("PENDING");
        }

        return service.listByStatus(status);
    }

   //Get project by Id
    @GetMapping("/projects/{id}")
    public ResearchProjectResponse get(@PathVariable Long id) {
        log.info("Fetching project details for projectId={}", id);
        return service.getProject(id);
    }

    //PM decision
    @PostMapping("/projects/{id}/decision")
    public ResponseEntity<List<String>> decide(
            @PathVariable Long id,
            @Valid @RequestBody ProgramManagerDecisionRequest req) {

        log.info("PM decision request received - projectId={}, decision={}", id, req.getDecision());

        ResearchProjectResponse after =
                service.decide(id, req.getDecision(), req.getReason(), req.getAmount());

        // APPROVED
        if ("APPROVED".equalsIgnoreCase(req.getDecision())) {

            log.info("Project approved. Grant issued. projectId={}, amount={}",
                    after.getProjectId(), req.getAmount());

            return ResponseEntity.ok(
                    List.of(
                            "Project approved successfully. Grant issued.",
                            "Amount: " + req.getAmount(),
                            "Project ID: " + after.getProjectId(),
                            "Status: " + after.getStatus()
                    )
            );
        }

        // REJECTED
        log.warn("Project rejected. projectId={}, reason={}", after.getProjectId(), after.getReason());

        return ResponseEntity.ok(
                List.of(
                        "Project rejected successfully.",
                        "Reason: " + after.getReason(),
                        "Project ID: " + after.getProjectId(),
                        "Status: " + after.getStatus()
                )
        );
    }
}