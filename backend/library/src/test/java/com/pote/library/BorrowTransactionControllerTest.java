package com.pote.library;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.pote.library.controller.BorrowTransactionController;
import com.pote.library.dto.BorrowRequestDTO;
import com.pote.library.entity.BorrowTransaction;
import com.pote.library.enums.BorrowStatus;
import com.pote.library.exception.BusinessLogicException;
import com.pote.library.service.BorrowTransactionService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(BorrowTransactionController.class) // Focuses only on the Controller
public class BorrowTransactionControllerTest {

	@Autowired
	private MockMvc mockMvc; // Simulates HTTP requests (Postman)

	@MockitoBean
	private BorrowTransactionService transactionService; // We fake the Service logic

	@Autowired
	private ObjectMapper objectMapper; // Converts Java Objects -> JSON

	private BorrowRequestDTO borrowRequest;
	private BorrowTransaction mockTransaction;

	@BeforeEach
	void setUp() {
		// Prepare dummy data for tests
		borrowRequest = new BorrowRequestDTO();
		borrowRequest.setQrCodeStr("BOOK-001");
		borrowRequest.setStudentIdCard("CS-2024-001");
		borrowRequest.setIssuedBy("Admin");

		mockTransaction = new BorrowTransaction();
		mockTransaction.setId(1L);
		mockTransaction.setStatus(BorrowStatus.ISSUED);
		mockTransaction.setIssueDate(LocalDateTime.now());
		mockTransaction.setDueDate(LocalDateTime.now().plusDays(14));
	}

	// --- TEST CASE 1: Successfully Issue a Book ---
	@Test
	void testIssueBook_Success() throws Exception {
		// GIVEN: The service returns a valid transaction
		when(transactionService.issueBook(any(BorrowRequestDTO.class))).thenReturn(mockTransaction);

		// WHEN & THEN: We hit the endpoint
		mockMvc.perform(post("/api/transactions/issue").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(borrowRequest))).andExpect(status().isOk()) // Expect 200 OK
				.andExpect(jsonPath("$.status").value("ISSUED")) // Check JSON response
				.andExpect(jsonPath("$.id").value(1));
	}

	// --- TEST CASE 2: Successfully Return a Book ---
	@Test
	void testReturnBook_Success() throws Exception {
		// Prepare return data
		mockTransaction.setStatus(BorrowStatus.RETURNED);
		mockTransaction.setReturnDate(LocalDateTime.now());

		// GIVEN: The service returns the updated transaction
		when(transactionService.returnBook("BOOK-001")).thenReturn(mockTransaction);

		// WHEN & THEN: We hit the endpoint (Using Query Param ?qrCode=...)
		mockMvc.perform(post("/api/transactions/return").param("qrCode", "BOOK-001")).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("RETURNED"));
	}

	// --- TEST CASE 3: Fail to Issue (Student Blocked) ---
	// This verifies your GlobalExceptionHandler works!
	@Test
	void testIssueBook_StudentBlocked_ThrowsException() throws Exception {
		// GIVEN: The service throws an exception (Business Rule Violation)
		when(transactionService.issueBook(any(BorrowRequestDTO.class)))
				.thenThrow(new BusinessLogicException("Student is BLOCKED. Cannot issue books."));

		// WHEN & THEN: We expect a 400 Bad Request
		mockMvc.perform(post("/api/transactions/issue").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(borrowRequest))).andExpect(status().isBadRequest()) // Expect
																												// 400
				.andExpect(jsonPath("$.error").value("Business Rule Violation"))
				.andExpect(jsonPath("$.message").value("Student is BLOCKED. Cannot issue books."));
	}
}