package com.cognizant.healthgov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.healthgov.model.Citizen;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Long> {

    boolean existsByContactInfo(String contactInfo);
}