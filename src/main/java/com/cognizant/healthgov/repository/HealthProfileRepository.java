package com.cognizant.healthgov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.healthgov.model.Citizen;
import com.cognizant.healthgov.model.HealthProfile;

@Repository
public interface HealthProfileRepository extends JpaRepository<HealthProfile, Long> {
   
    HealthProfile findByCitizen(Citizen citizen);
}