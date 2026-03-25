package com.healthgov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.healthgov.model.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> { }