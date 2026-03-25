package com.healthgov.service;

import com.healthgov.dto.ResearchProjectCreateRequest;
import com.healthgov.dto.ResearchProjectResponse;
import com.healthgov.dto.ResearchProjectUpdateRequest;
import com.healthgov.enums.ProjectStatus;
import com.healthgov.enums.GrantStatus;
import com.healthgov.exceptions.MedicalResearchException;
import com.healthgov.model.GrantApplication;
import com.healthgov.model.ResearchProject;
import com.healthgov.model.Users;
import com.healthgov.repository.GrantApplicationRepository;
import com.healthgov.repository.GrantRepository;
import com.healthgov.repository.ResearchProjectRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ResearchProjectServiceImpl implements ResearchProjectService {

    private final ResearchProjectRepository projectRepo;
    private final GrantRepository grantRepo;
    private final GrantApplicationRepository grantApplicationRepo;
    
    //Create project
    @Override
    public ResearchProjectResponse create(ResearchProjectCreateRequest req) {

        log.info("Creating new research project for researcherId={}", req.getResearcherId());
        
        //Validation — End date should not be before start date
        if (req.getEndDate().isBefore(req.getStartDate())) {
            log.error("Invalid dates: startDate={}, endDate={}", req.getStartDate(), req.getEndDate());
            throw new MedicalResearchException(HttpStatus.BAD_REQUEST,
                    "endDate must be on or after startDate");
        }
        
        //Fetch researcher
        Users researcher = grantApplicationRepo.findUserById(req.getResearcherId());
        if (researcher == null) {
            log.error("Researcher not found for researcherId={}", req.getResearcherId());
            throw new MedicalResearchException(HttpStatus.NOT_FOUND,
                    "Researcher not found: " + req.getResearcherId());
        }

        //Create Project Entity
        ResearchProject p = new ResearchProject();
        p.setTitle(req.getTitle());
        p.setDescription(req.getDescription());
        p.setResearcher(researcher);
        p.setStartDate(req.getStartDate());
        p.setEndDate(req.getEndDate());
        p.setStatus(ProjectStatus.PENDING);
        p.setReason(null);

        log.debug("Saving project into database...");
        ResearchProject saved = projectRepo.save(p);

        log.info("Project saved with projectId={}", saved.getProjectId());

        //Create Initial Grant Application
        GrantApplication ga = new GrantApplication();
        ga.setProject(saved);
        ga.setResearcher(researcher);
        ga.setSubmittedDate(LocalDate.now());
        ga.setStatus(GrantStatus.PENDING);
        grantApplicationRepo.save(ga);

        log.info("GrantApplication created (PENDING) for projectId={}", saved.getProjectId());

        //Convert Entity → DTO
        return toResponse(saved);
    }

    //Update project
    @Override
    public ResearchProjectResponse update(ResearchProjectUpdateRequest req) {

        log.info("Updating research project with projectId={}", req.getProjectId());
        
        //Fetch project by ID
        ResearchProject p = projectRepo.findById(req.getProjectId())
                .orElseThrow(() -> {
                    log.error("Project NOT found during update: projectId={}", req.getProjectId());
                    return new MedicalResearchException(HttpStatus.NOT_FOUND,
                            "Project not found: " + req.getProjectId());
                });

        //Approved projects cannot be updated
        if (p.getStatus() == ProjectStatus.APPROVED) {
            log.error("Update forbidden — project is already APPROVED. projectId={}", req.getProjectId());
            throw new MedicalResearchException(HttpStatus.BAD_REQUEST,
                    "Approved projects cannot be updated.");
        }

        // Rejected projects & pending projects can be updated
        if (req.getEndDate().isBefore(req.getStartDate())) {
            log.error("Invalid date range during update: startDate={}, endDate={}",
                    req.getStartDate(), req.getEndDate());
            throw new MedicalResearchException(HttpStatus.BAD_REQUEST,
                    "endDate must be >= startDate");
        }

        log.info("Project is {} → update allowed. Resetting to PENDING. projectId={}",
                p.getStatus(), req.getProjectId());

        //Update allowed fields
        p.setTitle(req.getTitle());
        p.setDescription(req.getDescription());
        p.setStartDate(req.getStartDate());
        p.setEndDate(req.getEndDate());
        p.setStatus(ProjectStatus.PENDING); 
        p.setReason(null);

        //Save updated project
        ResearchProject saved = projectRepo.save(p);
        log.info("Project updated successfully: projectId={}", saved.getProjectId());

        // Create new GrantApplication with PENDING status
        GrantApplication ga = new GrantApplication();
        ga.setProject(saved);
        ga.setResearcher(saved.getResearcher());
        ga.setSubmittedDate(LocalDate.now());
        ga.setStatus(GrantStatus.PENDING);
        grantApplicationRepo.save(ga);

        log.info("New GrantApplication created (PENDING) for projectId={}", saved.getProjectId());

        return toResponse(saved);
    }

   //List projects
    @Override
    @Transactional(readOnly = true)
    public List<ResearchProjectResponse> list(String status) {

        log.info("Fetching project list with status={}", status);

        List<ResearchProject> projects;

        if (status != null && !status.isBlank()) {
            try {
                ProjectStatus s = ProjectStatus.valueOf(status.toUpperCase());
                projects = projectRepo.findByStatus(s);
                log.info("Projects fetched by status {} -> count={}", status, projects.size());
            } catch (IllegalArgumentException e) {
                log.error("Invalid status parameter: {}", status);
                throw new MedicalResearchException(HttpStatus.BAD_REQUEST,
                        "Invalid status. Allowed: PENDING, APPROVED, REJECTED");
            }
        } else {
            projects = projectRepo.findAll();
            log.info("Fetched ALL projects -> count={}", projects.size());
        }

        return toResponseList(projects);
    }

    //Get project by id
    @Override
    @Transactional(readOnly = true)
    public ResearchProjectResponse get(Long id) {

        log.info("Fetching project details for projectId={}", id);

        ResearchProject p = projectRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Project NOT found: projectId={}", id);
                    return new MedicalResearchException(HttpStatus.NOT_FOUND,
                            "Project not found: " + id);
                });

        log.info("Project fetched successfully: projectId={}", id);

        return toResponse(p);
    }

    //Delete project
    @Override
    public void delete(Long id) {

        log.warn("Delete request received for projectId={}", id);

        ResearchProject p = projectRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Project NOT found during delete: projectId={}", id);
                    return new MedicalResearchException(HttpStatus.NOT_FOUND,
                            "Project not found: " + id);
                });

        if (p.getStatus() == ProjectStatus.PENDING) {

            log.info("Deleting project and related data for projectId={}", id);

            //Delete related grant + grant applications
            grantRepo.deleteByProject_ProjectId(id);
            grantApplicationRepo.deleteByProject_ProjectId(id);  
            //Delete project itself
            projectRepo.delete(p);

            log.info("Project and related grant data deleted: projectId={}", id);
            return;
        }

        log.error("Delete denied. Project not PENDING: projectId={}, status={}", id, p.getStatus());
        throw new MedicalResearchException(HttpStatus.CONFLICT,
                "Cannot delete project with APPROVED/REJECTED status or linked data.");
    }

   //Convert a ResearchProject entity into a ResearchProjectResponse DTO
    private ResearchProjectResponse toResponse(ResearchProject p) {
    	//Creates a new empty DTO object.
        ResearchProjectResponse r = new ResearchProjectResponse();
        //Copies from entity → DTO.
        r.setProjectId(p.getProjectId());
        r.setTitle(p.getTitle());
        r.setDescription(p.getDescription());
        r.setStartDate(p.getStartDate());
        r.setEndDate(p.getEndDate());
        r.setStatus(p.getStatus().name());
        r.setReason(p.getReason());
        r.setResearcherId(p.getResearcher().getUserId());
        r.setResearcherName(p.getResearcher().getName());
        return r;
    }

    private List<ResearchProjectResponse> toResponseList(List<ResearchProject> list) {
        List<ResearchProjectResponse> out = new ArrayList<>();
        for (ResearchProject p : list) out.add(toResponse(p));
        return out;
    }
}