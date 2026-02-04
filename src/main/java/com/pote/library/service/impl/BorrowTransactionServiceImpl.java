package com.pote.library.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pote.library.dto.BorrowRequestDTO;
import com.pote.library.entity.BookCopy;
import com.pote.library.entity.BorrowTransaction;
import com.pote.library.entity.Student;
import com.pote.library.enums.BookStatus;
import com.pote.library.enums.BorrowStatus;
import com.pote.library.enums.StudentStatus;
import com.pote.library.exception.BusinessLogicException;
import com.pote.library.exception.ResourceNotFoundException;
import com.pote.library.repository.BookCopyRepository;
import com.pote.library.repository.BorrowTransactionRepository;
import com.pote.library.repository.StudentRepository;
import com.pote.library.service.BorrowTransactionService;

@Service
public class BorrowTransactionServiceImpl implements BorrowTransactionService {

	private final BookCopyRepository bookCopyRepository;
	private final StudentRepository studentRepository;
	private final BorrowTransactionRepository transactionRepository;

	// --- MANUAL CONSTRUCTOR ---
	public BorrowTransactionServiceImpl(BookCopyRepository bookCopyRepository, StudentRepository studentRepository,
			BorrowTransactionRepository transactionRepository) {
		this.bookCopyRepository = bookCopyRepository;
		this.studentRepository = studentRepository;
		this.transactionRepository = transactionRepository;
	}

	@Override
	@Transactional // Ensures both the Transaction and BookCopy update together (or fail together)
	public BorrowTransaction issueBook(BorrowRequestDTO request) {

		// 1. Find the Student
		Student student = studentRepository.findByStudentIdCard(request.getStudentIdCard()).orElseThrow(
				() -> new ResourceNotFoundException("Student not found with ID: " + request.getStudentIdCard()));

		// 2. Validate Student Status
		if (student.getStatus() != StudentStatus.ACTIVE) {
			throw new BusinessLogicException("Student is " + student.getStatus() + ". Cannot issue books.");
		}

		// 3. Find the Book Copy
		BookCopy bookCopy = bookCopyRepository.findByQrCodeStr(request.getQrCodeStr()).orElseThrow(
				() -> new ResourceNotFoundException("Book Copy not found with QR: " + request.getQrCodeStr()));

		// 4. Validate Book Availability
		if (bookCopy.getStatus() != BookStatus.AVAILABLE) {
			throw new BusinessLogicException("This book copy is currently " + bookCopy.getStatus());
		}

		// 5. Create the Transaction
		BorrowTransaction transaction = new BorrowTransaction();
		transaction.setStudent(student);
		transaction.setBookCopy(bookCopy);
		transaction.setIssueDate(LocalDateTime.now());

		// Set Due Date (Default 14 days, or Librarian override)
		if (request.getDueDate() != null) {
			transaction.setDueDate(request.getDueDate());
		} else {
			transaction.setDueDate(LocalDateTime.now().plusDays(14));
		}

		transaction.setStatus(BorrowStatus.ISSUED);
		transaction.setIssuedBy(request.getIssuedBy());
		transaction.setFineAmount(0.0);
		transaction.setFinePaid(true); // No fine initially

		// 6. Update Book Copy Status (The DB Triggers will handle the counts)
		bookCopy.setStatus(BookStatus.BORROWED);
		bookCopyRepository.save(bookCopy);

		// 7. Save Transaction
		return transactionRepository.save(transaction);
	}

	@Override
	@Transactional
	public BorrowTransaction returnBook(String qrCodeStr) {

		// 1. Find the Copy
		BookCopy bookCopy = bookCopyRepository.findByQrCodeStr(qrCodeStr)
				.orElseThrow(() -> new ResourceNotFoundException("Book Copy not found with QR: " + qrCodeStr));

		// 2. Find the Active Transaction for this copy
		// We look for a transaction where this copy was used AND status is ISSUED
		BorrowTransaction transaction = transactionRepository.findAll().stream()
				.filter(t -> t.getBookCopy().getId().equals(bookCopy.getId()) && t.getStatus() == BorrowStatus.ISSUED)
				.findFirst()
				.orElseThrow(() -> new BusinessLogicException("This book is not currently issued to anyone."));

		// 3. Update Return Details
		transaction.setReturnDate(LocalDateTime.now());
		transaction.setStatus(BorrowStatus.RETURNED);
		transaction.setReceivedBy("Admin"); // In real app, get logged in user

		// 4. Update Book Copy Status
		bookCopy.setStatus(BookStatus.AVAILABLE);
		bookCopyRepository.save(bookCopy);

		// 5. Save (Your Database Triggers will automatically calculate the Fine Amount
		// now!)
		return transactionRepository.save(transaction);
	}

	@Override
	public BorrowTransaction renewBook(Long transactionId) {
	    
	    // 1. Find the transaction
	    BorrowTransaction transaction = transactionRepository.findById(transactionId)
	            .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with ID: " + transactionId));

	    if (transaction.getStatus() != BorrowStatus.ISSUED) {
	        throw new BusinessLogicException("Cannot renew. Book is already " + transaction.getStatus());
	    }

	    // 2. Define renewal period (e.g., 14 days)
	    int daysToExtend = 14; 

	    // 3. Call Procedure with the DAYS, not the DATE
	    transactionRepository.callRenewBookProcedure(transactionId, daysToExtend);

	    // 4. Return updated record
	    return transactionRepository.findById(transactionId).orElseThrow();
	}

	@Override
	public BorrowTransaction payFine(Long transactionId) {
		BorrowTransaction transaction = transactionRepository.findById(transactionId)
				.orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

		if (transaction.isFinePaid()) {
			throw new BusinessLogicException("Fine is already paid!");
		}

		if (transaction.getFineAmount() <= 0) {
			throw new BusinessLogicException("No fine exists for this transaction.");
		}

		transaction.setFinePaid(true);
		// Optionally: You could record the payment date or transaction reference here
		return transactionRepository.save(transaction);
	}
	
	@Override
	public List<BorrowTransaction> getAllTransactions() {
	    // Simply fetch everything from the table
	    return transactionRepository.findAll();
	}
}