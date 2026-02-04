package com.pote.library.dto;

import lombok.Data;

@Data
public class CategoryRequestDTO {
	private String name; // e.g., "Physics"
	private String description; // e.g., "Books about laws of motion"

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}