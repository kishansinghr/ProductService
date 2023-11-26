package com.kishan.productservice.dtos;

import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public GenericProductDto convertToGenericProductDto(FakeStoreDto fakeStoreDto) {
        GenericProductDto genericProductDto = new GenericProductDto();
        genericProductDto.setId(fakeStoreDto.getId());
        genericProductDto.setName(fakeStoreDto.getTitle());
        genericProductDto.setDescription(fakeStoreDto.getDescription());
        genericProductDto.setImage(fakeStoreDto.getImage());
        genericProductDto.setCategory(fakeStoreDto.getCategory());
        genericProductDto.setPrice(fakeStoreDto.getPrice());
        return genericProductDto;
    }

    public FakeStoreDto convertToFakeStoreDto(GenericProductDto genericProductDto) {
        FakeStoreDto fakeStoreDto = new FakeStoreDto();
        fakeStoreDto.setId(genericProductDto.getId());
        fakeStoreDto.setTitle(genericProductDto.getName());
        fakeStoreDto.setDescription(genericProductDto.getDescription());
        fakeStoreDto.setImage(genericProductDto.getImage());
        fakeStoreDto.setCategory(genericProductDto.getCategory());
        fakeStoreDto.setPrice(genericProductDto.getPrice());
        return fakeStoreDto;
    }
}
