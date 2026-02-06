package com.pote.library.controller;

import com.pote.library.dto.StudentRequestDTO;
import com.pote.library.entity.Student;
import com.pote.library.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // POST http://localhost:8080/api/students?creatorId=5
    @PostMapping
    public ResponseEntity<Student> registerStudent(
            @Valid @RequestBody StudentRequestDTO request,
            @RequestParam Long creatorId) { // This is the new required parameter
        Student newStudent = studentService.registerStudent(request, creatorId);
        return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
    }

    // GET http://localhost:8080/api/students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // GET http://localhost:8080/api/students/{studentIdCard}
    @GetMapping("/{studentIdCard}")
    public ResponseEntity<Student> getStudentByIdCard(@PathVariable String studentIdCard) {
        return ResponseEntity.ok(studentService.getStudentByCardId(studentIdCard));
    }
}