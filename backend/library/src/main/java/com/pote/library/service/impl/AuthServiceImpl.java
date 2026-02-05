package com.pote.library.service.impl;

import com.pote.library.dto.LoginRequestDTO;
import com.pote.library.entity.SystemUser;
import com.pote.library.exception.BusinessLogicException;
import com.pote.library.exception.ResourceNotFoundException;
import com.pote.library.repository.SystemUserRepository;
import com.pote.library.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

	private final SystemUserRepository userRepository;

	// Manual Constructor (Dependency Injection)
	public AuthServiceImpl(SystemUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public SystemUser login(LoginRequestDTO request) {
		// 1. Find User by Username
		SystemUser user = userRepository.findByUsername(request.getUsername()).orElseThrow(
				() -> new ResourceNotFoundException("User not found with username: " + request.getUsername()));

		// 2. Validate Password (Plain text check for now)
		if (!user.getPassword().equals(request.getPassword())) {
			throw new BusinessLogicException("Invalid username or password.");
		}

		// 3. Return User (Success)
		return user;
	}
}