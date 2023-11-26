package com.kishan.productservice.services;

import com.kishan.productservice.dtos.GenericProductDto;
import com.kishan.productservice.exceptions.ProductNotFoundException;
import com.kishan.productservice.exceptions.SomethingWentWrongException;

import java.util.List;

public interface ProductService {

    List<GenericProductDto> getAllProducts();

    GenericProductDto getProductById(long id) throws ProductNotFoundException, SomethingWentWrongException;

    GenericProductDto updateProductById(long id, GenericProductDto dto) throws ProductNotFoundException;

    GenericProductDto deleteProductById(long id) throws ProductNotFoundException, SomethingWentWrongException;

    GenericProductDto createProduct(GenericProductDto dto);
}
