package com.kishan.productservice.services.impls;

import com.kishan.productservice.dtos.GenericProductDto;
import com.kishan.productservice.exceptions.InvalidRequestException;
import com.kishan.productservice.exceptions.ProductNotFoundException;
import com.kishan.productservice.mappers.ProductMapper;
import com.kishan.productservice.models.Category;
import com.kishan.productservice.models.Price;
import com.kishan.productservice.models.Product;
import com.kishan.productservice.repositories.CategoryRepository;
import com.kishan.productservice.repositories.ProductRepository;
import com.kishan.productservice.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("productServiceDbImpl")
public class ProductServiceDBImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceDBImpl(ProductRepository productRepository,
                                CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<GenericProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper::convertProductModelToGenericProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public GenericProductDto getProductById(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.map(ProductMapper::convertProductModelToGenericProductDto)
                .orElse(null);
    }

    @Override
    public GenericProductDto updateProductById(long id, GenericProductDto dto) {
        Product productEntity = ProductMapper.convertGenericDtoToProductModel(dto);
        productEntity.setId(id);

        Price price1 = new Price();
        price1.setValue(dto.getPrice());
        price1.setCurrency(dto.getCurrency());
        productEntity.setPrice(price1);

        Category category = categoryRepository.findByName(dto.getCategory());
        if (category == null) {
            throw new InvalidRequestException("Wrong category name " + dto.getCategory());
        }

        productEntity.setCategory(category);
        Product savedProduct = productRepository.save(productEntity);
        return ProductMapper.convertProductModelToGenericProductDto(savedProduct);
    }

    @Override
    public GenericProductDto deleteProductById(long id) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty())
            throw new ProductNotFoundException(id);

        productRepository.delete(optionalProduct.get());
        return ProductMapper.convertProductModelToGenericProductDto(optionalProduct.get());
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto dto) throws InvalidRequestException {
        Product productEntity = ProductMapper.convertGenericDtoToProductModel(dto);

        Price price = new Price();
        price.setValue(dto.getPrice());
        price.setCurrency("INR");
        productEntity.setPrice(price);

        Category category = categoryRepository.findByName(dto.getCategory());
        if (category == null) {
            throw new InvalidRequestException("Wrong category name " + dto.getCategory());
        }
        productEntity.setCategory(category);

        Product savedProduct = productRepository.save(productEntity);
        return ProductMapper.convertProductModelToGenericProductDto(savedProduct);
    }
}
