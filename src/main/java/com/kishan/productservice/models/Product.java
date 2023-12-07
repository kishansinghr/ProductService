package com.kishan.productservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "products")
public class Product extends  BaseModel {
    private String name;
    private String description;
    private  String image;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Price price;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;
}
