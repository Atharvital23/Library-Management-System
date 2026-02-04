package com.pote.library.service;

import com.pote.library.dto.StudentRequestDTO;
import com.pote.library.entity.Student;
import java.util.List;

public interface StudentService {

	// Register a new student
	Student registerStudent(StudentRequestDTO request);

	// Get all students
	List<Student> getAllStudents();

	// Find by College ID Card (e.g. "CS-2024-001")
	Student getStudentByCardId(String studentIdCard);
}