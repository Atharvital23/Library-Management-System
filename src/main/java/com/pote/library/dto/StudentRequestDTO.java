package com.pote.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class StudentRequestDTO {

	@NotBlank(message = "Student ID Card is required")
	private String studentIdCard;

	@NotBlank(message = "First name cannot be empty")
	private String firstName;

	@NotBlank(message = "Last name cannot be empty")
	private String lastName;

	@Email(message = "Invalid email format")
	@NotBlank(message = "Email is required")
	private String email;

	@Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
	private String phoneNumber;

	private String department;
	private String role;

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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}