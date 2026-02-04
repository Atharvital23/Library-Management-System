package com.pote.library.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pote.library.dto.CategoryRequestDTO;
import com.pote.library.entity.Category;
import com.pote.library.exception.BusinessLogicException;
import com.pote.library.exception.ResourceNotFoundException;
import com.pote.library.repository.CategoryRepository;
import com.pote.library.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	// --- MANUAL CONSTRUCTOR ---
	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public Category addCategory(CategoryRequestDTO request) {
		// 1. Check if name exists (Duplicate Check)
		if (categoryRepository.existsByName(request.getName())) {
			throw new BusinessLogicException("Category '" + request.getName() + "' already exists.");
		}

		// 2. Save
		Category category = new Category();
		category.setName(request.getName());
		category.setDescription(request.getDescription());

		return categoryRepository.save(category);
	}

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public Category getCategoryById(Integer id) {
		return categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));
	}

	@Override
	public void deleteCategory(Integer id) {
		// 1. Find it first
		Category category = getCategoryById(id);

		// 2. Validation: In a real app, you'd check if books exist here first.
		// For now, if the DB constraint fails, GlobalExceptionHandler will catch it.
		categoryRepository.delete(category);
	}
}