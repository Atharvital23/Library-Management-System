package com.pote.library.service.impl;

import com.pote.library.dto.SystemUserRequestDTO;
import com.pote.library.entity.SystemUser;
import com.pote.library.enums.SystemRole;
import com.pote.library.enums.SystemStatus;
import com.pote.library.exception.BusinessLogicException;
import com.pote.library.exception.ResourceNotFoundException;
import com.pote.library.repository.SystemUserRepository;
import com.pote.library.service.SystemUserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SystemUserServiceImpl implements SystemUserService {

	private final SystemUserRepository systemUserRepository;

	public SystemUserServiceImpl(SystemUserRepository systemUserRepository) {
		this.systemUserRepository = systemUserRepository;
	}

	@Override
	public List<SystemUser> getAllUsers() {
		return systemUserRepository.findAll();
	}

	@Override
	public SystemUser registerUser(SystemUserRequestDTO request, Long creatorId) {

		// Authorization Check: Find the Creator
		SystemUser creator = systemUserRepository.findById(creatorId)
				.orElseThrow(() -> new ResourceNotFoundException("Creator not found with ID: " + creatorId));

		// Validate Role: Only LIBRARIAN can create new users
		if (creator.getRole() != SystemRole.LIBRARIAN) {
			throw new BusinessLogicException("Access Denied. Only a LIBRARIAN can register new system users.");
		}

		// Check if Username exists
		if (systemUserRepository.findByUsername(request.getUsername()).isPresent()) {
			throw new BusinessLogicException("Username " + request.getUsername() + " is already taken.");
		}

		// Map and Save
		SystemUser user = new SystemUser();
		user.setUsername(request.getUsername());
		user.setPassword(request.getPassword());
		user.setFullName(request.getFullName());

		try {
			user.setRole(request.getRole());
		} catch (IllegalArgumentException e) {
			throw new BusinessLogicException("Invalid Role.");
		}

		user.setStatus(SystemStatus.ACTIVE);
		user.setCreatedAt(LocalDateTime.now());
		user.setCreatedBy(creator.getUsername()); // Track who created this user

		return systemUserRepository.save(user);
	}
}