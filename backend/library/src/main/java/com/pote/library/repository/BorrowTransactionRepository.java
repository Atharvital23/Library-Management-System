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

	/**
	 * Find all active loans for a specific student (Status = ISSUED)
	 * 
	 * @param studentId
	 * @param status
	 * 
	 *                  It will returns the List of object of BorrowTransaction
	 *                  class
	 * @return
	 */
	List<BorrowTransaction> findByStudentIdAndStatus(Long studentId, BookStatus status);

	/**
	 * Find all transactions for a specific student
	 * 
	 * @param studentId
	 * 
	 *                  It will returns the List of object of BorrowTransaction
	 *                  class
	 * @return
	 */
	List<BorrowTransaction> findByStudentId(Long studentId);

	@Modifying
	@Transactional
	@Query(value = "CALL renew_book(:transactionId, :extraDays)", nativeQuery = true)
	/**
	 * Calling Procedure in the sql defines We pass the number of days to add (e.g.,
	 * 14)
	 * 
	 * @param transactionId
	 * @param extraDays
	 */
	void callRenewBookProcedure(@Param("transactionId") Long transactionId, @Param("extraDays") Integer extraDays);

	/**
	 * Find all transactions by Student ID Card
	 * 
	 * @param studentIdCard
	 * 
	 *                      It will returns the List of object of BorrowTransaction
	 *                      class
	 * @return
	 */
	List<BorrowTransaction> findByStudent_StudentIdCard(String studentIdCard);
}