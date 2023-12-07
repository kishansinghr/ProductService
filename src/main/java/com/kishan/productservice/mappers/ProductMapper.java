package com.kishan.productservice.mappers;

import com.kishan.productservice.dtos.FakeStoreDto;
import com.kishan.productservice.dtos.GenericProductDto;
import com.kishan.productservice.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public static GenericProductDto convertToGenericProductDto(FakeStoreDto fakeStoreDto) {
        GenericProductDto genericProductDto = new GenericProductDto();
        genericProductDto.setId(fakeStoreDto.getId());
        genericProductDto.setName(fakeStoreDto.getTitle());
        genericProductDto.setDescription(fakeStoreDto.getDescription());
        genericProductDto.setImage(fakeStoreDto.getImage());
        genericProductDto.setCategory(fakeStoreDto.getCategory());
        genericProductDto.setPrice(fakeStoreDto.getPrice());
        return genericProductDto;
    }

    public static GenericProductDto convertProductModelToGenericProductDto(Product product) {
        GenericProductDto genericProductDto = new GenericProductDto();
        genericProductDto.setId(product.getId());
        genericProductDto.setName(product.getName());
        genericProductDto.setDescription(product.getDescription());
        genericProductDto.setImage(product.getImage());
        genericProductDto.setCategory(product.getCategory().getName());
        genericProductDto.setPrice(product.getPrice().getValue());
        genericProductDto.setCurrency(product.getPrice().getCurrency());
        return genericProductDto;
    }

    public static FakeStoreDto convertToFakeStoreDto(GenericProductDto genericProductDto) {
        FakeStoreDto fakeStoreDto = new FakeStoreDto();
        fakeStoreDto.setId(genericProductDto.getId());
        fakeStoreDto.setTitle(genericProductDto.getName());
        fakeStoreDto.setDescription(genericProductDto.getDescription());
        fakeStoreDto.setImage(genericProductDto.getImage());
        fakeStoreDto.setCategory(genericProductDto.getCategory());
        fakeStoreDto.setPrice((int) genericProductDto.getPrice());
        return fakeStoreDto;
    }

    public static Product convertGenericDtoToProductModel(GenericProductDto genericProductDto) {
        Product productEntity = new Product();
        productEntity.setId(genericProductDto.getId());
        productEntity.setName(genericProductDto.getName());
        productEntity.setDescription(genericProductDto.getDescription());
        productEntity.setImage(genericProductDto.getImage());
        return productEntity;
    }
}
