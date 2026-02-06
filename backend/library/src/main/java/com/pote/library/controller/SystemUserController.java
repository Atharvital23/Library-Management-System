package com.pote.library.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pote.library.dto.SystemUserRequestDTO;
import com.pote.library.entity.SystemUser;
import com.pote.library.service.SystemUserService;

@RestController
@RequestMapping("/api/users")
public class SystemUserController {

	private final SystemUserService systemUserService;

	public SystemUserController(SystemUserService systemUserService) {
		this.systemUserService = systemUserService;
	}

	// POST http://localhost:8080/api/users/register?creatorId=1
	@PostMapping("/register")
	public ResponseEntity<SystemUser> registerUser(@RequestBody SystemUserRequestDTO request,
			@RequestParam Long creatorId) {
		return new ResponseEntity<>(systemUserService.registerUser(request, creatorId), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<SystemUser>> getAllUsers() {
		return ResponseEntity.ok(systemUserService.getAllUsers());
	}
}