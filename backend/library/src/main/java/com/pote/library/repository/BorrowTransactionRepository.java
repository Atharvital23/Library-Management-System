package com.pote.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pote.library.entity.BorrowTransaction;
import com.pote.library.enums.BookStatus;

import jakarta.transaction.Transactional;

public interface BorrowTransactionRepository extends JpaRepository<BorrowTransaction, Long> {

	// 1. Find all active loans for a specific student (Status = ISSUED)
	List<BorrowTransaction> findByStudentIdAndStatus(Long studentId, BookStatus status);

	// 2. History: Find all transactions for a specific student
	List<BorrowTransaction> findByStudentId(Long studentId);

	// OLD (Wrong)
	// void callRenewBookProcedure(Long transactionId, LocalDateTime newDueDate);

	// NEW (Correct) -> We pass the number of days to add (e.g., 14)
	@Modifying
	@Transactional
	@Query(value = "CALL renew_book(:transactionId, :extraDays)", nativeQuery = true)
	void callRenewBookProcedure(@Param("transactionId") Long transactionId, @Param("extraDays") Integer extraDays);
}