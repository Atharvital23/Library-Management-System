package com.pote.library.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pote.library.dto.BorrowRequestDTO;
import com.pote.library.entity.BorrowTransaction;
import com.pote.library.service.BorrowTransactionService;

@RestController
@RequestMapping("/api/transactions")
public class BorrowTransactionController {

	private final BorrowTransactionService transactionService;

	public BorrowTransactionController(BorrowTransactionService transactionService) {
		this.transactionService = transactionService;
	}

	// 1. Issue a Book
	// POST http://localhost:8080/api/transactions/issue
	@PostMapping("/issue")
	public ResponseEntity<BorrowTransaction> issueBook(@RequestBody BorrowRequestDTO request) {
		return ResponseEntity.ok(transactionService.issueBook(request));
	}

	// 2. Return a Book
	// POST http://localhost:8080/api/transactions/return?qrCode=BOOK-001
	@PostMapping("/return")
	public ResponseEntity<BorrowTransaction> returnBook(@RequestParam String qrCode) {
		return ResponseEntity.ok(transactionService.returnBook(qrCode));
	}

	// 3. Renew a Book
	// POST http://localhost:8080/api/transactions/renew/5
	@PostMapping("/renew/{transactionId}")
	public ResponseEntity<BorrowTransaction> renewBook(@PathVariable Long transactionId) {
		BorrowTransaction updatedTransaction = transactionService.renewBook(transactionId);
		return ResponseEntity.ok(updatedTransaction);
	}
	
	// POST http://localhost:8080/api/transactions/pay-fine/5
	@PostMapping("/pay-fine/{transactionId}")
	public ResponseEntity<BorrowTransaction> payFine(@PathVariable Long transactionId) {
	    return ResponseEntity.ok(transactionService.payFine(transactionId));
	}
	
	// 4. Get All Transactions
	// GET http://localhost:8080/api/transactions
	@GetMapping
	public ResponseEntity<List<BorrowTransaction>> getAllTransactions() {
	    return ResponseEntity.ok(transactionService.getAllTransactions());
	}
}