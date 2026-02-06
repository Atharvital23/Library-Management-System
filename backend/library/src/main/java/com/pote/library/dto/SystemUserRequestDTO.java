package com.pote.library.dto;

import com.pote.library.enums.SystemRole;

import lombok.Data;

@Data
public class SystemUserRequestDTO {
	private String username;
	private String password;
	private String fullName;
	private SystemRole role;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public SystemRole getRole() {
		return role;
	}

	public void setRole(SystemRole role) {
		this.role = role;
	}

}