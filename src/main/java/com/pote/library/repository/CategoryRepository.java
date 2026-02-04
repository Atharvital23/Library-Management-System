package com.pote.library.repository;

import com.pote.library.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	// Custom query: Check if a category exists before creating a duplicate
	boolean existsByName(String name);
}