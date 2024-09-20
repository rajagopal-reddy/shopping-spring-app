package com.trainingmug.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trainingmug.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String email);

}
