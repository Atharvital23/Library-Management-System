package com.pote.library.service;

import com.pote.library.dto.CategoryRequestDTO;
import com.pote.library.entity.Category;
import java.util.List;

public interface CategoryService {

	/**
	 * Create a new category (e.g., "Computer Science")
	 * 
	 * @param request
	 * 
	 *                Returns the Object of Category class
	 * @return
	 */
	Category addCategory(CategoryRequestDTO request);

	/**
	 * Get all categories
	 * 
	 * @return
	 */
	List<Category> getAllCategories();

	/**
	 * Get single category
	 * 
	 * @param id
	 * 
	 *           Returns the Object of Category class
	 * @return
	 */
	Category getCategoryById(Integer id);

	/**
	 * Delete a category Note:-> Only if no books are linked to it!
	 * 
	 * @param id
	 */
	void deleteCategory(Integer id);
}