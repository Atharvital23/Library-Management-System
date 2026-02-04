package com.pote.library.service.impl;

import com.pote.library.entity.Book;
import com.pote.library.entity.BookCopy;
import com.pote.library.enums.BookStatus;
import com.pote.library.exception.BusinessLogicException;
import com.pote.library.exception.ResourceNotFoundException;
import com.pote.library.repository.BookCopyRepository;
import com.pote.library.repository.BookRepository;
import com.pote.library.service.BookCopyService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookCopyServiceImpl implements BookCopyService {

	private final BookCopyRepository bookCopyRepository;
	private final BookRepository bookRepository;

	// --- MANUAL CONSTRUCTOR ---
	public BookCopyServiceImpl(BookCopyRepository bookCopyRepository, BookRepository bookRepository) {
		this.bookCopyRepository = bookCopyRepository;
		this.bookRepository = bookRepository;
	}

	@Override
	public BookCopy addBookCopy(Long bookId, String qrCodeStr, String shelfLocation) {

		// 1. Find the Parent Book (Title)
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));

		// 2. Check if QR Code is unique
		if (bookCopyRepository.findByQrCodeStr(qrCodeStr).isPresent()) {
			throw new BusinessLogicException("QR Code " + qrCodeStr + " is already assigned to another book.");
		}

		// 3. Create the Physical Copy
		BookCopy copy = new BookCopy();
		copy.setBook(book); // Link to parent
		copy.setQrCodeStr(qrCodeStr); // The unique sticker
		copy.setShelfLocation(shelfLocation);
		copy.setStatus(BookStatus.AVAILABLE); // Ready to be borrowed
		copy.setAddedAt(LocalDateTime.now());
		copy.setConditionNote("New");
		copy.setReference(false); // Default to borrowing allowed

		// 4. Save (Triggers in DB will update the "Total Copies" count on the parent
		// Book!)
		return bookCopyRepository.save(copy);
	}

	@Override
	public List<BookCopy> getCopiesByBookId(Long bookId) {
		// You might need to add this custom method to your Repository first!
		// If 'findByBookId' doesn't exist in Repository, we can use a filter or add it.
		// Let's add the method to the Repository in the next step just to be safe.
		return bookCopyRepository.findAll().stream().filter(copy -> copy.getBook().getId().equals(bookId)).toList();
	}
}