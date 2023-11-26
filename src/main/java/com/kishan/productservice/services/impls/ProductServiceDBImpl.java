package com.kishan.productservice.services.impls;

import com.kishan.productservice.dtos.GenericProductDto;
import com.kishan.productservice.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productServiceDbImpl")
public class ProductServiceDBImpl implements ProductService {
    @Override
    public List<GenericProductDto> getAllProducts() {
        return null;
    }

    @Override
    public GenericProductDto getProductById(long id) {
        return null;
    }

    @Override
    public GenericProductDto updateProductById(long id, GenericProductDto dto) {
        return null;
    }

    @Override
    public GenericProductDto deleteProductById(long id) {
        return null;
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto dto) {
        return null;
    }
}
