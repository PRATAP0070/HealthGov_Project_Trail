package com.healthgov.repository;

import com.healthgov.enums.ProjectStatus;
import com.healthgov.model.ResearchProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResearchProjectRepository extends JpaRepository<ResearchProject, Long> {

    List<ResearchProject> findByStatus(ProjectStatus status);

    //List<ResearchProject> findAll();
}
