package com.pote.library.controller;

import com.pote.library.entity.BookCopy;
import com.pote.library.service.BookCopyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/copies")
public class BookCopyController {

	private final BookCopyService bookCopyService;

	public BookCopyController(BookCopyService bookCopyService) {
		this.bookCopyService = bookCopyService;
	}

	// 1. Add a Copy
	// POST http://localhost:8080/api/copies/add?bookId=1&qrCode=BOOK-001&shelf=A1
	@PostMapping("/add")
	public ResponseEntity<BookCopy> addBookCopy(@RequestParam Long bookId, @RequestParam String qrCode,
			@RequestParam String shelf) {

		BookCopy newCopy = bookCopyService.addBookCopy(bookId, qrCode, shelf);
		return ResponseEntity.ok(newCopy);
	}

	// 2. View Copies of a Book
	// GET http://localhost:8080/api/copies/book/1
	@GetMapping("/book/{bookId}")
	public ResponseEntity<List<BookCopy>> getCopiesByBookId(@PathVariable Long bookId) {
		return ResponseEntity.ok(bookCopyService.getCopiesByBookId(bookId));
	}
}