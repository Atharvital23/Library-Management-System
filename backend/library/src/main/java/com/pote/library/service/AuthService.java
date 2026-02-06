package com.pote.library.service;

import com.pote.library.dto.LoginRequestDTO;
import com.pote.library.entity.SystemUser;

public interface AuthService {
	
	/**
	 * Login for LIBRARIAN, ADMIN values
	 * @param request 
	 * 
	 * It will return SystemUser Object
	 * @return
	 */
	SystemUser login(LoginRequestDTO request);
}