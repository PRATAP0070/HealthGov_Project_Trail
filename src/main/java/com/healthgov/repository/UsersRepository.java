package com.healthgov.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthgov.model.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

}
