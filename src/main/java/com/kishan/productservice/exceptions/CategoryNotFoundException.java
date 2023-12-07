package com.kishan.productservice.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(long id) {
        super("Category not found by id " + id + ".");
    }
}
