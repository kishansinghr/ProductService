package com.kishan.productservice.dtos;

import com.kishan.productservice.models.Category;
import lombok.Data;

@Data
public class GenericProductDto {
    private long id;
    private String name;
    private String description;
    private int price;
    private String category;
    private  String image;
}