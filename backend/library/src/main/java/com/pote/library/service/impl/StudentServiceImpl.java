package com.pote.library.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pote.library.dto.StudentRequestDTO;
import com.pote.library.entity.Student;
import com.pote.library.enums.Department;
import com.pote.library.enums.Role;
import com.pote.library.enums.StudentStatus;
import com.pote.library.exception.BusinessLogicException;
import com.pote.library.exception.ResourceNotFoundException;
import com.pote.library.repository.StudentRepository;
import com.pote.library.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;

	// --- MANUAL CONSTRUCTOR (Fixes Null Error) ---
	public StudentServiceImpl(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@Override
	public Student registerStudent(StudentRequestDTO request) {

		// 1. Check if Student ID Card already exists
		if (studentRepository.findByStudentIdCard(request.getStudentIdCard()).isPresent()) {
			throw new BusinessLogicException("Student with ID Card " + request.getStudentIdCard() + " already exists.");
		}

		// 2. Check if Email already exists
		if (studentRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new BusinessLogicException("Email " + request.getEmail() + " is already registered.");
		}

		// 3. Map DTO to Entity
		Student student = new Student();
		student.setStudentIdCard(request.getStudentIdCard());
		student.setFirstName(request.getFirstName());
		student.setLastName(request.getLastName());
		student.setEmail(request.getEmail());
		student.setPhoneNumber(request.getPhoneNumber());

		// Convert String to Enum (e.g., "Computer_Science" ->
		// Department.Computer_Science)
		try {
			student.setDepartment(Department.valueOf(request.getDepartment()));
			student.setRole(Role.valueOf(request.getRole()));
		} catch (IllegalArgumentException e) {
			throw new BusinessLogicException("Invalid Department or Role provided.");
		}

		// Set Defaults
		student.setStatus(StudentStatus.ACTIVE);
		student.setCreatedAt(LocalDateTime.now());
		// In a real app, you'd get the logged-in admin's name here
		student.setCreatedBy("Admin");

		return studentRepository.save(student);
	}

	@Override
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	@Override
	public Student getStudentByCardId(String studentIdCard) {
		return studentRepository.findByStudentIdCard(studentIdCard)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found with Card ID: " + studentIdCard));
	}
}