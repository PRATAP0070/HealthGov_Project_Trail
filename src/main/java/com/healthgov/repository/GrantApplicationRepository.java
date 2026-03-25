package com.healthgov.repository;

import com.healthgov.model.GrantApplication;
import com.healthgov.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GrantApplicationRepository extends JpaRepository<GrantApplication, Long> {
	
	//Counts how many grant applications exist for a specific project.
    long countByProject_ProjectId(Long projectId);
    
    //Deletes ALL grant applications linked to a specific project
    void deleteByProject_ProjectId(Long projectId);

    //Fetches the latest grant application for a project
    GrantApplication findTopByProject_ProjectIdOrderByApplicationIdDesc(Long projectId);

    @Query("SELECT u FROM Users u WHERE u.userId = :id")
    Users findUserById(@Param("id") Long id);
}