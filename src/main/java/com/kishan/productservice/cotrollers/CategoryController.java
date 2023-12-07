package com.kishan.productservice.cotrollers;

import com.kishan.productservice.dtos.CategoryDto;
import com.kishan.productservice.exceptions.CategoryNotFoundException;
import com.kishan.productservice.services.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public CategoryDto createCategory(@RequestBody CategoryDto dto) {
        return categoryService.createCategory(dto);
    }

    @PutMapping
    public CategoryDto updateCategory(@RequestBody CategoryDto dto) {
        return categoryService.updateCategory(dto);
    }

    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable("id") long id) {
        return categoryService.getCategoryById(id);
    }

    @DeleteMapping("/{id}")
    public CategoryDto deleteCategoryById(@PathVariable("id") long id) throws CategoryNotFoundException {
        return categoryService.deleteCategoryById(id);
    }

    @GetMapping
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
