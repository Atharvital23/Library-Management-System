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

	public BookCopyServiceImpl(BookCopyRepository bookCopyRepository, BookRepository bookRepository) {
		this.bookCopyRepository = bookCopyRepository;
		this.bookRepository = bookRepository;
	}

	@Override
	public BookCopy addBookCopy(Long bookId, String qrCodeStr, String shelfLocation) {

		// Find the Parent Book (Title)
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));

		// Check if QR Code is unique
		if (bookCopyRepository.findByQrCodeStr(qrCodeStr).isPresent()) {
			throw new BusinessLogicException("QR Code " + qrCodeStr + " is already assigned to another book.");
		}

		// Create the Physical Copy
		BookCopy copy = new BookCopy();
		copy.setBook(book); // Link to parent
		copy.setQrCodeStr(qrCodeStr); // The unique sticker
		copy.setShelfLocation(shelfLocation);

		// Default Values
		copy.setStatus(BookStatus.AVAILABLE); // Ready to be borrowed
		copy.setAddedAt(LocalDateTime.now());
		copy.setConditionNote("New");
		copy.setReference(false); // Default to borrowing allowed

		return bookCopyRepository.save(copy);
	}

	@Override
	public List<BookCopy> getCopiesByBookId(Long bookId) {

		return bookCopyRepository.findAll().stream().filter(copy -> copy.getBook().getId().equals(bookId)).toList();
	}
}