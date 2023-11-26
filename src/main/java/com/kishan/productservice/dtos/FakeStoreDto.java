package com.kishan.productservice.dtos;

import com.kishan.productservice.models.Category;
import lombok.Data;

@Data
public class FakeStoreDto {
    private long id;
    private String title;
    private String description;
    private int price;
    private String category;
    private  String image;
}
