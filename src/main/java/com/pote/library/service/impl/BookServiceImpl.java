package com.pote.library.service.impl;

import com.pote.library.dto.BookRequestDTO;
import com.pote.library.entity.Book;
import com.pote.library.entity.Category;
import com.pote.library.exception.BusinessLogicException;
import com.pote.library.exception.ResourceNotFoundException;
import com.pote.library.repository.BookRepository;
import com.pote.library.repository.CategoryRepository;
import com.pote.library.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    // --- MANUAL CONSTRUCTOR INJECTION (Fixes the Null Error) ---
    public BookServiceImpl(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Book addBook(BookRequestDTO request) {
        
        // 1. Validation
        if (bookRepository.findByIsbn(request.getIsbn()).isPresent()) {
            throw new BusinessLogicException("A book with ISBN " + request.getIsbn() + " already exists.");
        }

        // 2. Find Category
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + request.getCategoryId()));

        // 3. Map DTO to Entity
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setEdition(request.getEdition());
        book.setPublisher(request.getPublisher());
        book.setPublicationYear(request.getPublicationYear());
        book.setLanguage(request.getLanguage());
        book.setDescription(request.getDescription());
        book.setCoverImageUrl(request.getCoverImageUrl());
        book.setCategory(category); // Link the category
        
        book.setTotalCopies(0);
        book.setAvailableCopies(0);

        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        // This was the line causing the error. Now it will work!
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));
    }
    
    @Override
    public Book getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ISBN: " + isbn));
    }
}