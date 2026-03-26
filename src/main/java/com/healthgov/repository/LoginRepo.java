package com.healthgov.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthgov.model.Users;



public interface LoginRepo extends JpaRepository<Users, Long>{

	Users findByName(String name);

}