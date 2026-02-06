package com.pote.library.repository;

import com.pote.library.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {

	/**
	 * Find user for Login
	 * 
	 * @param username
	 * 
	 *                 Return the SystemUser object
	 * @return
	 */
	Optional<SystemUser> findByUsername(String username);
}