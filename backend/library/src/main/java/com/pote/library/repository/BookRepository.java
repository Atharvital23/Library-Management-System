package com.pote.library.repository;

import com.pote.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
	// Custom query: Find a book by its unique ISBN
	Optional<Book> findByIsbn(String isbn);
}