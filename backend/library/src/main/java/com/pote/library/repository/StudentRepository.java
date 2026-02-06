package com.pote.library.repository;

import com.pote.library.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

	/**
	 * Find by College ID Card (e.g., "CS-2024-001")
	 * 
	 * @param studentIdCard
	 * 
	 *                      It will returns the object of the Student class
	 * @return
	 */
	Optional<Student> findByStudentIdCard(String studentIdCard);

	/**
	 * Find by Email (for notifications or login)
	 * 
	 * @param email
	 * 
	 *              It will returns the object of the Student class
	 * @return
	 */
	Optional<Student> findByEmail(String email);
}