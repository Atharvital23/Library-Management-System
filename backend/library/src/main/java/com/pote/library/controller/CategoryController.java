package com.pote.library.controller;

import com.pote.library.dto.CategoryRequestDTO;
import com.pote.library.entity.Category;
import com.pote.library.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	// Add Category
	// POST http://localhost:8080/api/categories
	@PostMapping
	public ResponseEntity<Category> addCategory(@RequestBody CategoryRequestDTO request) {
		Category newCategory = categoryService.addCategory(request);
		return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
	}

	// Get All Categories
	// GET http://localhost:8080/api/categories
	@GetMapping
	public ResponseEntity<List<Category>> getAllCategories() {
		return ResponseEntity.ok(categoryService.getAllCategories());
	}

	// Delete Category
	// DELETE http://localhost:8080/api/categories/1
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
		categoryService.deleteCategory(id);
		return ResponseEntity.noContent().build(); // Returns 204 No Content
	}
}