package com.kishan.productservice.mappers;

import com.kishan.productservice.dtos.CategoryDto;
import com.kishan.productservice.models.Category;

public class CategoryMapper {
    public static Category convertDtoToEntity(CategoryDto dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        return category;
    }

    public static CategoryDto convertEntityToDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }
}
