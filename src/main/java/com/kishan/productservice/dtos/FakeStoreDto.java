package com.kishan.productservice.dtos;

import lombok.Data;

@Data
public class FakeStoreDto {
    private long id;
    private String title;
    private String description;
    private int price;
    private String category;
    private String image;
}
