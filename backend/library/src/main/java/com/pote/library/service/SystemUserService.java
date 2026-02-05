package com.pote.library.service;

import com.pote.library.dto.SystemUserRequestDTO;
import com.pote.library.entity.SystemUser;
import java.util.List;

public interface SystemUserService {

	// Register a new Admin or Librarian
	SystemUser registerUser(SystemUserRequestDTO request);

	// Get all system users
	List<SystemUser> getAllUsers();
	
	SystemUser registerUser(SystemUserRequestDTO request, Long creatorId);
}