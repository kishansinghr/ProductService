package com.kishan.productservice.cotrollers;

import com.kishan.productservice.dtos.GenericProductDto;
import com.kishan.productservice.exceptions.ProductNotFoundException;
import com.kishan.productservice.exceptions.SomethingWentWrongException;
import com.kishan.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(@Qualifier("fakeStoreProductService") ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public GenericProductDto createProduct(@RequestBody GenericProductDto dto) {
        return productService.createProduct(dto);
    }

    @GetMapping
    public List<GenericProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public GenericProductDto getProductById(@PathVariable("id") long id) throws ProductNotFoundException, SomethingWentWrongException {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public GenericProductDto updateProduct(@PathVariable("id") long id, @RequestBody GenericProductDto productDto) throws ProductNotFoundException {
        return productService.updateProductById(id, productDto);
    }

    @DeleteMapping("/{id}")
    public GenericProductDto deleteProductById(@PathVariable("id") long id) throws ProductNotFoundException, SomethingWentWrongException {
        return productService.deleteProductById(id);
    }

}
