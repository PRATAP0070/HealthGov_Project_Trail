package com.healthgov.service;

import com.healthgov.dto.ResearchProjectCreateRequest;
import com.healthgov.dto.ResearchProjectUpdateRequest;
import com.healthgov.dto.ResearchProjectResponse;

import java.util.List;

public interface ResearchProjectService {
	
    ResearchProjectResponse create(ResearchProjectCreateRequest req);

    ResearchProjectResponse update(ResearchProjectUpdateRequest req);

    //fetch by status
    List<ResearchProjectResponse> list(String status);
    
    //Fetch one project by ID
    ResearchProjectResponse get(Long id);

    //delete by id
    void delete(Long id);
}   