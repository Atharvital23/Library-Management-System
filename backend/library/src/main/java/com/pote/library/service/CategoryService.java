package com.pote.library.service;

import com.pote.library.dto.CategoryRequestDTO;
import com.pote.library.entity.Category;
import java.util.List;

public interface CategoryService {

	// Create a new category (e.g., "Computer Science")
	Category addCategory(CategoryRequestDTO request);

	// Get all categories (for the dropdown list in frontend)
	List<Category> getAllCategories();

	// Get single category
	Category getCategoryById(Integer id);

	// Delete a category (Only if no books are linked to it!)
	void deleteCategory(Integer id);
}