package com.kishan.productservice.thirdparty;

import com.kishan.productservice.dtos.FakeStoreDto;
import com.kishan.productservice.dtos.ProductMapper;
import com.kishan.productservice.exceptions.ProductNotFoundException;
import com.kishan.productservice.exceptions.SomethingWentWrongException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class FakeStoreClient {
    private final String FAKE_STORE_GENERIC_URL = "https://fakestoreapi.com/products";
    private final String FAKE_STORE_PRODUCT_SPECIFIC_URL = "https://fakestoreapi.com/products/{id}";

    private final RestTemplateBuilder restTemplateBuilder;
    @Autowired
    FakeStoreClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    
    public List<FakeStoreDto> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreDto[]> responseEntity = restTemplate.getForEntity(FAKE_STORE_GENERIC_URL, FakeStoreDto[].class);

        List<FakeStoreDto> productList = new ArrayList<>();
        if(responseEntity.getBody()!=null)
        Collections.addAll(productList, responseEntity.getBody());
        return productList;
    }

    
    public FakeStoreDto getProductById(long id) throws SomethingWentWrongException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreDto> responseEntity = restTemplate.getForEntity(FAKE_STORE_PRODUCT_SPECIFIC_URL, FakeStoreDto.class, id);
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity.getBody();
        } else {
            throw new SomethingWentWrongException("Something went wrong");
        }
    }

    
    public FakeStoreDto updateProductById(long id, FakeStoreDto requestDto)  throws ProductNotFoundException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        HttpEntity<FakeStoreDto> requestEntity = new HttpEntity<>(requestDto, new HttpHeaders());
        ResponseEntity<FakeStoreDto> responseEntity = restTemplate.exchange(FAKE_STORE_PRODUCT_SPECIFIC_URL, HttpMethod.PUT, requestEntity, FakeStoreDto.class, id);

        if(responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity.getBody();
        } else {
            throw new ProductNotFoundException("Product not found by id "+id+".");
        }
    }

    
    public FakeStoreDto deleteProductById(long id) throws ProductNotFoundException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        HttpEntity<FakeStoreDto> requestEntity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<FakeStoreDto> responseEntity
                = restTemplate.exchange(FAKE_STORE_PRODUCT_SPECIFIC_URL,
                HttpMethod.DELETE,
                requestEntity,
                FakeStoreDto.class,
                id);
        if(responseEntity.getBody() != null) {
            return responseEntity.getBody();
        } else {
            throw new ProductNotFoundException("Product not found by id "+id+".");
        }
    }

    
    public FakeStoreDto createProduct(FakeStoreDto requestDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreDto> responseEntity = restTemplate.postForEntity(FAKE_STORE_GENERIC_URL, requestDto, FakeStoreDto.class);
        return responseEntity.getBody();
    }
}
