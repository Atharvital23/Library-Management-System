package com.pote.library.service;

import com.pote.library.dto.StudentRequestDTO;
import com.pote.library.entity.Student;
import java.util.List;

public interface StudentService {

	/**
	 * Get all students
	 * 
	 * @return
	 */
	List<Student> getAllStudents();

	/**
	 * Find by College ID Card (e.g. "CS-2024-001")
	 * 
	 * @param studentIdCard
	 * 
	 *                      Returns the Student class object
	 * @return
	 */
	Student getStudentByCardId(String studentIdCard);

	/**
	 * Register a new student
	 * 
	 * @param request
	 * @param creatorId
	 * 
	 *                  Returns the Student class object
	 * @return
	 */
	Student registerStudent(StudentRequestDTO request, Long creatorId);
}