package com.kishan.productservice.services.impls;

import com.kishan.productservice.dtos.FakeStoreDto;
import com.kishan.productservice.dtos.GenericProductDto;
import com.kishan.productservice.dtos.ProductMapper;
import com.kishan.productservice.exceptions.ProductNotFoundException;
import com.kishan.productservice.exceptions.SomethingWentWrongException;
import com.kishan.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {
    private final String FAKE_STORE_GENERIC_URL = "https://fakestoreapi.com/products";
    private final String FAKE_STORE_PRODUCT_SPECIFIC_URL = "https://fakestoreapi.com/products/{id}";

    private RestTemplateBuilder restTemplateBuilder;
    private ProductMapper productMapper;
    @Autowired
    FakeStoreProductService(RestTemplateBuilder restTemplateBuilder, ProductMapper productMapper) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.productMapper = productMapper;
    }

    @Override
    public List<GenericProductDto> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreDto[]> responseEntity = restTemplate.getForEntity(FAKE_STORE_GENERIC_URL, FakeStoreDto[].class);

        List<GenericProductDto> productList = new ArrayList<>();
        for (FakeStoreDto fakeStoreDto :
                responseEntity.getBody()) {
            productList.add(productMapper.convertToGenericProductDto(fakeStoreDto));
        }
        return productList;
    }

    @Override
    public GenericProductDto getProductById(long id) throws ProductNotFoundException, SomethingWentWrongException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreDto> responseEntity = restTemplate.getForEntity(FAKE_STORE_PRODUCT_SPECIFIC_URL, FakeStoreDto.class, id);
        if(responseEntity.getBody() == null) {
            throw new ProductNotFoundException("Product not found by id "+id+".");
        } else if(responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return productMapper.convertToGenericProductDto(responseEntity.getBody());
        } else {
            throw new SomethingWentWrongException("Something went wrong");
        }
    }

    @Override
    public GenericProductDto updateProductById(long id, GenericProductDto dto)  throws ProductNotFoundException {
        FakeStoreDto requestDto = productMapper.convertToFakeStoreDto(dto);
        RestTemplate restTemplate = restTemplateBuilder.build();
        HttpEntity<FakeStoreDto> requestEntity = new HttpEntity<>(requestDto, new HttpHeaders());
        ResponseEntity<FakeStoreDto> responseEntity = restTemplate.exchange(FAKE_STORE_PRODUCT_SPECIFIC_URL, HttpMethod.PUT, requestEntity, FakeStoreDto.class, id);

        if(responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return productMapper.convertToGenericProductDto(responseEntity.getBody());
        } else {
            throw new ProductNotFoundException("Product not found by id "+id+".");
        }
    }

    @Override
    public GenericProductDto deleteProductById(long id) throws ProductNotFoundException, SomethingWentWrongException {
        GenericProductDto productDto = this.getProductById(id);
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.delete(FAKE_STORE_PRODUCT_SPECIFIC_URL, id);
        return productDto;
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto dto) {
        FakeStoreDto requestDto = productMapper.convertToFakeStoreDto(dto);
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreDto> responseEntity = restTemplate.postForEntity(FAKE_STORE_GENERIC_URL, requestDto, FakeStoreDto.class);
        return productMapper.convertToGenericProductDto(responseEntity.getBody());
    }
}
