package com.pote.library.service;

import com.pote.library.entity.BookCopy;
import java.util.List;

public interface BookCopyService {

	// Add a new physical copy (Admin scans a new QR code sticker)
	BookCopy addBookCopy(Long bookId, String qrCodeStr, String shelfLocation);

	// Get all copies of a specific book (e.g., Show all 5 copies of "Harry Potter")
	List<BookCopy> getCopiesByBookId(Long bookId);
}