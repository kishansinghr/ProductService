package com.kishan.productservice.services;

import com.kishan.productservice.dtos.GenericProductDto;
import com.kishan.productservice.exceptions.ProductNotFoundException;
import com.kishan.productservice.exceptions.SomethingWentWrongException;

import java.util.List;

public interface ProductService {

    public List<GenericProductDto> getAllProducts();
    public GenericProductDto getProductById(long id) throws ProductNotFoundException, SomethingWentWrongException;
    public GenericProductDto updateProductById(long id, GenericProductDto dto) throws ProductNotFoundException;
    public  GenericProductDto deleteProductById(long id) throws ProductNotFoundException, SomethingWentWrongException;
    public GenericProductDto createProduct(GenericProductDto dto);
}
