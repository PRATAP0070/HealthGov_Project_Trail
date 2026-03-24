package com.healthgov.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthgov.model.User;



public interface LoginRepo extends JpaRepository<User, Long>{

	User findByName(String name);

}