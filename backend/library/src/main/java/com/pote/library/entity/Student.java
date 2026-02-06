package com.pote.library.entity;

import java.time.LocalDateTime;
import com.pote.library.enums.Department;
import com.pote.library.enums.Role;
import com.pote.library.enums.StudentStatus; // You need to create this Enum file

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "student")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "student_id_card", nullable = false, unique = true)
	private String studentIdCard;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(name = "phone_number")
	private String phoneNumber;

	// Maps "Computer_Science" etc.
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Department department;

	// Maps "STUDENT" or "TEACHER"
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	// Maps "ACTIVE" or "INACTIVE"
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private StudentStatus status;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStudentIdCard() {
		return studentIdCard;
	}

	public void setStudentIdCard(String studentIdCard) {
		this.studentIdCard = studentIdCard;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public StudentStatus getStatus() {
		return status;
	}

	public void setStatus(StudentStatus status) {
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