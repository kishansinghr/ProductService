package com.kishan.productservice.services.impls;

import com.kishan.productservice.dtos.CategoryDto;
import com.kishan.productservice.exceptions.CategoryNotFoundException;
import com.kishan.productservice.mappers.CategoryMapper;
import com.kishan.productservice.models.Category;
import com.kishan.productservice.repositories.CategoryRepository;
import com.kishan.productservice.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto createCategory(CategoryDto dto) {
        Category categoryEntity = CategoryMapper.convertDtoToEntity(dto);
        Category savedCategory = categoryRepository.save(categoryEntity);
        return CategoryMapper.convertEntityToDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto dto) {
        Category categoryEntity = CategoryMapper.convertDtoToEntity(dto);
        Category savedCategory = categoryRepository.save(categoryEntity);
        return CategoryMapper.convertEntityToDto(savedCategory);
    }

    @Override
    public CategoryDto getCategoryById(long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.map(CategoryMapper::convertEntityToDto)
                .orElseThrow();
    }

    @Override
    public CategoryDto deleteCategoryById(long id) throws CategoryNotFoundException {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isEmpty())
            throw new CategoryNotFoundException(id);

        return CategoryMapper.convertEntityToDto(optionalCategory.get());
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> allCategories = categoryRepository.findAll();
        return allCategories.stream()
                .map(CategoryMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }
}
