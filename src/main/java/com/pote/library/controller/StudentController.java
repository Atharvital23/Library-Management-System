package com.pote.library.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pote.library.dto.StudentRequestDTO;
import com.pote.library.entity.Student;
import com.pote.library.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/students")
public class StudentController {

	private final StudentService studentService;

	// --- MANUAL CONSTRUCTOR ---
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	// 1. Register Student
	@PostMapping
	public ResponseEntity<Student> registerStudent(@Valid @RequestBody StudentRequestDTO request) {
		Student newStudent = studentService.registerStudent(request);
		return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
	}

	// 2. Get All Students	
	@GetMapping
	public ResponseEntity<List<Student>> getAllStudents() {
		return ResponseEntity.ok(studentService.getAllStudents());
	}

	// 3. Get Student by Card ID
	@GetMapping("/{cardId}")
	public ResponseEntity<Student> getStudentByCardId(@PathVariable String cardId) {
		return ResponseEntity.ok(studentService.getStudentByCardId(cardId));
	}
}