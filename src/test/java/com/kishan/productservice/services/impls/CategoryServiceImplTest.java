package com.kishan.productservice.services.impls;

import com.kishan.productservice.dtos.CategoryDto;
import com.kishan.productservice.mappers.CategoryMapper;
import com.kishan.productservice.models.Category;
import com.kishan.productservice.repositories.CategoryRepository;
import com.kishan.productservice.services.CategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@SpringBootTest
class CategoryServiceImplTest {

//    @MockBean
    CategoryRepository categoryRepository;

//    @Inject
    CategoryService categoryService;

    @BeforeEach
    void init() {
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryServiceImpl(categoryRepository);
    }
//    @BeforeEach
//    void setUp() {
//    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createCategory() {

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Mobiles");

        Category category = CategoryMapper.convertDtoToEntity(categoryDto);
        category.setId(101);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        //Test create method
        CategoryDto generatedCategory = categoryService.createCategory(categoryDto);

        assertNotNull(generatedCategory);
        assertEquals(generatedCategory.getId(), category.getId());
    }

    @Test
    void updateCategory() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Updated Mobiles");

        Category category = CategoryMapper.convertDtoToEntity(categoryDto);
        category.setId(101);
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        //Test create method
        CategoryDto generatedCategory = categoryService.updateCategory(categoryDto);

        assertNotNull(generatedCategory);
        assertEquals(generatedCategory.getName(), category.getName());
    }

    @Test
    void getCategoryById() {
        Category category = new Category();
        category.setId(101);
        category.setName("Mobiles");
        when(categoryRepository.findById(101L)).thenReturn(Optional.of(category));
        when(categoryRepository.findById(102L)).thenReturn(Optional.empty());

        CategoryDto categoryDto = categoryService.getCategoryById(101);
        assertEquals(categoryDto.getName(), category.getName());

        assertThrows(Exception.class, () -> categoryService.getCategoryById(102));
    }

    @Test
    void deleteCategoryById() {
        Category category = new Category();
        category.setId(101);
        category.setName("Mobiles");
        when(categoryRepository.findById(101L)).thenReturn(Optional.of(category));

        CategoryDto categoryDto = categoryService.deleteCategoryById(101);
        assertEquals(categoryDto.getName(), category.getName());
    }

    @Test
    void getAllCategories() {

        Category category = new Category();
        category.setId(101);
        category.setName("Mobiles");

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        when(categoryRepository.findAll()).thenReturn(categoryList);

        List<CategoryDto> categories = categoryService.getAllCategories();
        assertEquals(1, categories.size());
    }
}