package com.pote.library.entity;

import java.time.LocalDateTime;
import com.pote.library.enums.SystemRole;
import com.pote.library.enums.SystemStatus;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "system_user")
public class SystemUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	// Maps "ADMIN" or "LIBRARIAN"
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SystemRole role;

	@Column(name = "full_name", nullable = false)
	private String fullName;

	// Maps "ACTIVE" or "INACTIVE"
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SystemStatus status;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public SystemRole getRole() {
		return role;
	}

	public void setRole(SystemRole role) {
		this.role = role;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public SystemStatus getStatus() {
		return status;
	}

	public void setStatus(SystemStatus status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}