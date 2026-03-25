package com.healthgov.repository;

import com.healthgov.model.Grants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrantRepository extends JpaRepository<Grants, Long> {

    long countByProject_ProjectId(Long projectId);

    void deleteByProject_ProjectId(Long projectId);
}