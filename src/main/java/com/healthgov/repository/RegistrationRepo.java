package com.healthgov.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthgov.model.User;



public interface RegistrationRepo extends JpaRepository<User, Long>{

}
