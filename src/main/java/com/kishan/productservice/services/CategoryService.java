package com.kishan.productservice.services;

import com.kishan.productservice.dtos.CategoryDto;
import com.kishan.productservice.exceptions.CategoryNotFoundException;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto dto);

    CategoryDto updateCategory(CategoryDto dto);

    CategoryDto getCategoryById(long id);

    CategoryDto deleteCategoryById(long id) throws CategoryNotFoundException;

    List<CategoryDto> getAllCategories();
}
