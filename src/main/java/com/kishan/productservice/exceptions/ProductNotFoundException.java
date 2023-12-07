package com.kishan.productservice.exceptions;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(long id) {
        super("Product not found by id " + id + ".");
    }
}
