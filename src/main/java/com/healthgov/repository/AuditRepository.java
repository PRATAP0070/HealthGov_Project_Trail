package com.healthgov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.healthgov.model.Audit;

@Repository
@Transactional
public interface AuditRepository extends JpaRepository<Audit, Long> {

	

}
