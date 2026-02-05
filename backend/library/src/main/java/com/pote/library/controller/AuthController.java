package com.pote.library.controller;

import com.pote.library.dto.LoginRequestDTO;
import com.pote.library.entity.SystemUser;
import com.pote.library.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	// Manual Constructor
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	// Login Endpoint
	// POST http://localhost:8080/api/auth/login
	@PostMapping("/login")
	public ResponseEntity<SystemUser> login(@Valid @RequestBody LoginRequestDTO request) {
		return ResponseEntity.ok(authService.login(request));
	}
}