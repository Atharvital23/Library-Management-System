package com.pote.library.repository;

import com.pote.library.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SystemUserRepository extends JpaRepository<SystemUser, Integer> {
	// Find user for Login
	Optional<SystemUser> findByUsername(String username);
}