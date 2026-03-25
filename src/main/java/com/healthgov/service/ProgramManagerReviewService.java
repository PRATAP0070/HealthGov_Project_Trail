package com.healthgov.service;

import com.healthgov.dto.ResearchProjectResponse;

import java.math.BigDecimal;
import java.util.List;

public interface ProgramManagerReviewService {

    // Fetch all pending projects
    List<ResearchProjectResponse> listPending();

    // Fetch projects based on status
    List<ResearchProjectResponse> listByStatus(String status);

    // Fetch a single project by ID
    ResearchProjectResponse getProject(Long projectId);

    // Program Manager decision
    ResearchProjectResponse decide(Long projectId, String decision, String reason, Double amount);
}