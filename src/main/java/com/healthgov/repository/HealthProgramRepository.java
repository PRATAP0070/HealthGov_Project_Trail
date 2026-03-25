package com.healthgov.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthgov.model.HealthProgram;

public interface HealthProgramRepository extends JpaRepository<HealthProgram, Long>{

}
