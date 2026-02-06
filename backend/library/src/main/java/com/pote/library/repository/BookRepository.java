package com.pote.library.repository;

import com.pote.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

	/**
	 * Find a book by its unique ISBN
	 * 
	 * @param isbn
	 * 
	 *             return the book object
	 * @return
	 */
	Optional<Book> findByIsbn(String isbn);
}