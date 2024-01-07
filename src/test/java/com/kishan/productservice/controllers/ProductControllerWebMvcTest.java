package com.kishan.productservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kishan.productservice.cotrollers.ProductController;
import com.kishan.productservice.dtos.GenericProductDto;
import com.kishan.productservice.models.Product;
import com.kishan.productservice.services.ProductService;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(ProductController.class)
public class ProductControllerWebMvcTest {

    @Inject
    MockMvc mockMvc;

    @Inject
    ObjectMapper objectMapper;

    @MockBean(name="productServiceDbImpl")
    ProductService productService;

    @Test
    void testGetAllProducts() throws Exception {

        Mockito.when(productService.getAllProducts())
                        .thenReturn(new ArrayList<>());

        mockMvc.perform(get("/products"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetProductById() throws Exception {

        GenericProductDto productDto = new GenericProductDto();
        productDto.setId(101);
        productDto.setName("Iphone 14");
        productDto.setDescription("Last year iphone");
        productDto.setPrice(100000);
        productDto.setCurrency("INR");
        productDto.setCategory("Mobile");

        Mockito.when(productService.getProductById(101L))
                .thenReturn(productDto);

        ResultActions resultActions = mockMvc.perform(get("/products/101"));

        resultActions.andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(productDto)));
    }
}
