package com.kishan.productservice.services.impls;

import com.kishan.productservice.dtos.FakeStoreDto;
import com.kishan.productservice.dtos.GenericProductDto;
import com.kishan.productservice.dtos.ProductMapper;
import com.kishan.productservice.exceptions.ProductNotFoundException;
import com.kishan.productservice.exceptions.SomethingWentWrongException;
import com.kishan.productservice.services.ProductService;
import com.kishan.productservice.thirdparty.FakeStoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {

    private final FakeStoreClient fakeStoreClient;
    private final ProductMapper productMapper;

    @Autowired
    FakeStoreProductService(FakeStoreClient fakeStoreClient, ProductMapper productMapper) {
        this.fakeStoreClient = fakeStoreClient;
        this.productMapper = productMapper;
    }

    @Override
    public List<GenericProductDto> getAllProducts() {
        List<FakeStoreDto> fakeStoreProducts = fakeStoreClient.getAllProducts();
        List<GenericProductDto> productList = new ArrayList<>();
        for (FakeStoreDto fakeStoreDto : fakeStoreProducts) {
            productList.add(productMapper.convertToGenericProductDto(fakeStoreDto));
        }
        return productList;
    }

    @Override
    public GenericProductDto getProductById(long id) throws ProductNotFoundException, SomethingWentWrongException {
        FakeStoreDto fakeStoreDto = fakeStoreClient.getProductById(id);
        if (fakeStoreDto == null) {
            throw new ProductNotFoundException("Product not found by id " + id + ".");
        } else {
            return productMapper.convertToGenericProductDto(fakeStoreDto);
        }
    }

    @Override
    public GenericProductDto updateProductById(long id, GenericProductDto dto) throws ProductNotFoundException {
        FakeStoreDto fakeStoreDto
                = fakeStoreClient.updateProductById(id, productMapper.convertToFakeStoreDto(dto));
        if (fakeStoreDto != null) {
            return productMapper.convertToGenericProductDto(fakeStoreDto);
        } else {
            throw new ProductNotFoundException("Product not found by id " + id + ".");
        }
    }

    @Override
    public GenericProductDto deleteProductById(long id) throws ProductNotFoundException {
        FakeStoreDto fakeStoreDto = fakeStoreClient.deleteProductById(id);
        if (fakeStoreDto != null) {
            return productMapper.convertToGenericProductDto(fakeStoreDto);
        } else {
            throw new ProductNotFoundException("Product not found by id " + id + ".");
        }
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto dto) {
        FakeStoreDto requestDto = productMapper.convertToFakeStoreDto(dto);
        FakeStoreDto responseProduct = fakeStoreClient.createProduct(requestDto);
        return productMapper.convertToGenericProductDto(responseProduct);
    }
}
