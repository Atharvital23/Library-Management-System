package com.pote.library.service;

import java.util.List;

import com.pote.library.dto.BorrowRequestDTO;
import com.pote.library.entity.BorrowTransaction;

public interface BorrowTransactionService {

	/**
	 * Issue a book to a student
	 * 
	 * @param request
	 * 
	 *                return the object of BorrowTransaction
	 * @return
	 */
	BorrowTransaction issueBook(BorrowRequestDTO request);

	/**
	 * Return a book (Using the QR Code of the copy)
	 * 
	 * @param qrCodeStr
	 * 
	 *                  return the object of BorrowTransaction
	 * @return
	 */
	BorrowTransaction returnBook(String qrCodeStr);

	/**
	 * We have to renew the issue date by procedure in sql
	 * 
	 * @param transactionId
	 * 
	 *                      It will return the same transaction
	 * @return
	 */
	BorrowTransaction renewBook(Long transactionId);

	/**
	 * It is for your due date is gone and for renewal of the book then we take the
	 * charges as an fine 20/day
	 * 
	 * @param transactionId
	 * 
	 *                      It will return the same transaction
	 * @return
	 */
	BorrowTransaction payFine(Long transactionId);

	/**
	 * Get all transaction history (returns the List of Transaction)
	 * 
	 * @return
	 */
	List<BorrowTransaction> getAllTransactions();
}