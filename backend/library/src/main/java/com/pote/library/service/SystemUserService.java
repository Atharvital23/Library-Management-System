package com.pote.library.service;

import com.pote.library.dto.SystemUserRequestDTO;
import com.pote.library.entity.SystemUser;
import java.util.List;

public interface SystemUserService {

	/**
	 * Get all system users
	 * 
	 * @return
	 */
	List<SystemUser> getAllUsers();

	/**
	 * Register a new Admin or Librarian
	 * 
	 * @param request
	 * @param creatorId
	 * 
	 *                  It will return the SystemUser object
	 * @return
	 */
	SystemUser registerUser(SystemUserRequestDTO request, Long creatorId);
}