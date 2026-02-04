package com.pote.library.service;

import com.pote.library.dto.BookRequestDTO;
import com.pote.library.entity.Book;
import java.util.List;

public interface BookService {

	// Create a new book from the DTO
	Book addBook(BookRequestDTO bookRequest);

	// Get a list of all books
	List<Book> getAllBooks();

	// Find a specific book (or throw error if not found)
	Book getBookById(Long id);

	// Find a book by ISBN
	Book getBookByIsbn(String isbn);
}