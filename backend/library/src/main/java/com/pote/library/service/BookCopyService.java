package com.pote.library.service;

import com.pote.library.entity.BookCopy;
import java.util.List;

public interface BookCopyService {

	/**
	 * Add a new physical copy
	 * 
	 * @param bookId
	 * @param qrCodeStr
	 * @param shelfLocation
	 * 
	 *                      It returns the same object
	 * @return
	 * 
	 */
	BookCopy addBookCopy(Long bookId, String qrCodeStr, String shelfLocation);

	/**
	 * Get all copies of a specific book
	 * 
	 * @param bookId
	 * 
	 *               It will return the List of book object from the BookId
	 * @return
	 */
	List<BookCopy> getCopiesByBookId(Long bookId);
}