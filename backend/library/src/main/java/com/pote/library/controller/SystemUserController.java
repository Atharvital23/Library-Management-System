package com.pote.library.controller;

import com.pote.library.dto.SystemUserRequestDTO;
import com.pote.library.entity.SystemUser;
import com.pote.library.service.SystemUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class SystemUserController {

	private final SystemUserService systemUserService;

	public SystemUserController(SystemUserService systemUserService) {
		this.systemUserService = systemUserService;
	}

	// 1. Create a New Admin/Librarian
	// POST http://localhost:8080/api/users/register
	@PostMapping("/register")
	public ResponseEntity<SystemUser> registerUser(@RequestBody SystemUserRequestDTO request) {
		return new ResponseEntity<>(systemUserService.registerUser(request), HttpStatus.CREATED);
	}

	// 2. View All Users
	// GET http://localhost:8080/api/users
	@GetMapping
	public ResponseEntity<List<SystemUser>> getAllUsers() {
		return ResponseEntity.ok(systemUserService.getAllUsers());
	}

	// POST http://localhost:8080/api/users/register?creatorId=1
	@PostMapping("/register")
	public ResponseEntity<SystemUser> registerUser(@RequestBody SystemUserRequestDTO request,
			@RequestParam Long creatorId) { // Get ID from URL
		return new ResponseEntity<>(systemUserService.registerUser(request, creatorId), HttpStatus.CREATED);
	}
}