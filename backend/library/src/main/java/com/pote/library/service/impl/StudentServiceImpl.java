package com.pote.library.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pote.library.dto.StudentProfileDTO;
import com.pote.library.dto.StudentRequestDTO;
import com.pote.library.entity.BorrowTransaction;
import com.pote.library.entity.Student;
import com.pote.library.entity.SystemUser;
import com.pote.library.enums.Department;
import com.pote.library.enums.Role;
import com.pote.library.enums.StudentStatus;
import com.pote.library.enums.SystemRole;
import com.pote.library.exception.BusinessLogicException;
import com.pote.library.exception.ResourceNotFoundException;
import com.pote.library.repository.BorrowTransactionRepository;
import com.pote.library.repository.StudentRepository;
import com.pote.library.repository.SystemUserRepository;
import com.pote.library.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;
	private final SystemUserRepository systemUserRepository;
	private final BorrowTransactionRepository transactionRepository;

	public StudentServiceImpl(StudentRepository studentRepository, SystemUserRepository systemUserRepository,
			BorrowTransactionRepository transactionRepository) {
		this.studentRepository = studentRepository;
		this.systemUserRepository = systemUserRepository;
		this.transactionRepository = transactionRepository;
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

	@Override
	public Student registerStudent(StudentRequestDTO request, Long creatorId) {

		// Authorization Check: Find the Creator
		SystemUser creator = systemUserRepository.findById(creatorId)
				.orElseThrow(() -> new ResourceNotFoundException("Creator not found with ID: " + creatorId));

		// Validate Role: Only ADMIN can create Students
		if (creator.getRole() != SystemRole.ADMIN) {
			throw new BusinessLogicException("Access Denied. Only an ADMIN can register students.");
		}

		if (studentRepository.findByStudentIdCard(request.getStudentIdCard()).isPresent()) {
			throw new BusinessLogicException("Student ID Card already exists.");
		}

		Student student = new Student();
		// Map fields
		student.setStudentIdCard(request.getStudentIdCard());
		student.setFirstName(request.getFirstName());
		student.setLastName(request.getLastName());
		student.setEmail(request.getEmail());
		student.setPhoneNumber(request.getPhoneNumber());
		student.setDepartment(Department.valueOf(request.getDepartment()));
		student.setRole(Role.valueOf(request.getRole()));

		student.setStatus(StudentStatus.ACTIVE);
		student.setCreatedAt(LocalDateTime.now());
		student.setCreatedBy(creator.getUsername());

		return studentRepository.save(student);
	}

	@Override
	public StudentProfileDTO getStudentProfile(String studentIdCard) {
		// Get Student Details
		Student student = studentRepository.findByStudentIdCard(studentIdCard)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studentIdCard));

		// Get Transaction History
		List<BorrowTransaction> history = transactionRepository.findByStudent_StudentIdCard(studentIdCard);

		// Calculate Total Unpaid Fines
		double totalFine = history.stream().filter(t -> !t.isFinePaid() && t.getFineAmount() != null)
				.mapToDouble(BorrowTransaction::getFineAmount).sum();

		// Build DTO
		StudentProfileDTO profile = new StudentProfileDTO();
		profile.setStudentDetails(student);
		profile.setTransactionHistory(history);
		profile.setTotalFineOwed(totalFine);

		return profile;
	}
}