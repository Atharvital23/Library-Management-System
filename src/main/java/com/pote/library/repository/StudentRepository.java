package com.pote.library.repository;

import com.pote.library.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
	// Find by College ID Card (e.g., "CS-2024-001")
	Optional<Student> findByStudentIdCard(String studentIdCard);

	// Find by Email (for notifications or login)
	Optional<Student> findByEmail(String email);
}