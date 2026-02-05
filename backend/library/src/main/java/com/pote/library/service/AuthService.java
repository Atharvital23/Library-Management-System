package com.pote.library.service;

import com.pote.library.dto.LoginRequestDTO;
import com.pote.library.entity.SystemUser;

public interface AuthService {

	// Abstract method for login
	SystemUser login(LoginRequestDTO request);
}