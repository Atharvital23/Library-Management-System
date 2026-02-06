package com.pote.library.service;

import com.pote.library.dto.BookRequestDTO;
import com.pote.library.entity.Book;
import java.util.List;

public interface BookService {

	/**
	 * Create a new book from the DTO
	 * 
	 * @param bookRequest
	 * 
	 *                    It will return Book object
	 * @return
	 */
	Book addBook(BookRequestDTO bookRequest);

	/**
	 * Get a list of all books (returns the List of Book object)
	 * 
	 * @return
	 */
	List<Book> getAllBooks();

	/**
	 * Find a specific book (or throw error if not found)
	 * 
	 * @param id
	 * 
	 *           returns the Book object
	 * @return
	 */
	Book getBookById(Long id);

	/**
	 * Find a book by ISBN
	 * 
	 * @param isbn
	 * 
	 *             returns the Book object
	 * @return
	 */
	Book getBookByIsbn(String isbn);
}