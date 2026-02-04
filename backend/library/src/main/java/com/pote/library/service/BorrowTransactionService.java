package com.pote.library.service;

import java.util.List;

import com.pote.library.dto.BorrowRequestDTO;
import com.pote.library.entity.BorrowTransaction;

public interface BorrowTransactionService {

	// Issue a book to a student
	BorrowTransaction issueBook(BorrowRequestDTO request);

	// Return a book (Using the QR Code of the copy)
	BorrowTransaction returnBook(String qrCodeStr);

	// Add this line to the interface
	BorrowTransaction renewBook(Long transactionId);

	BorrowTransaction payFine(Long transactionId);
	
	// Get all transaction history
	List<BorrowTransaction> getAllTransactions();
}