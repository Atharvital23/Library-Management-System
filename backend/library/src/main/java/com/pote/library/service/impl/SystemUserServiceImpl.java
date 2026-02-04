package com.pote.library.service.impl;

import com.pote.library.dto.SystemUserRequestDTO;
import com.pote.library.entity.SystemUser;
import com.pote.library.enums.SystemRole;
import com.pote.library.enums.SystemStatus;
import com.pote.library.exception.BusinessLogicException;
import com.pote.library.repository.SystemUserRepository;
import com.pote.library.service.SystemUserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SystemUserServiceImpl implements SystemUserService {

	private final SystemUserRepository systemUserRepository;

	// --- MANUAL CONSTRUCTOR ---
	public SystemUserServiceImpl(SystemUserRepository systemUserRepository) {
		this.systemUserRepository = systemUserRepository;
	}

	@Override
	public SystemUser registerUser(SystemUserRequestDTO request) {

		// 1. Check if Username exists
		if (systemUserRepository.findByUsername(request.getUsername()).isPresent()) {
			throw new BusinessLogicException("Username " + request.getUsername() + " is already taken.");
		}

		// 2. Map DTO to Entity
		SystemUser user = new SystemUser();
		user.setUsername(request.getUsername());
		user.setPassword(request.getPassword()); // TODO: Encrypt this later when adding Security!
		user.setFullName(request.getFullName());

		try {
			user.setRole(SystemRole.valueOf(request.getRole())); // "ADMIN" or "LIBRARIAN"
		} catch (IllegalArgumentException e) {
			throw new BusinessLogicException("Invalid Role. Must be ADMIN or LIBRARIAN.");
		}

		user.setStatus(SystemStatus.ACTIVE);
		user.setCreatedAt(LocalDateTime.now());
		user.setCreatedBy("SuperAdmin"); // Hardcoded for now

		return systemUserRepository.save(user);
	}

	@Override
	public List<SystemUser> getAllUsers() {
		return systemUserRepository.findAll();
	}
}