package com.kishan.productservice.models;

//import jakarta.persistence.Id;
//import jakarta.persistence.MappedSuperclass;

import lombok.Data;

import java.util.Date;

@Data
//@MappedSuperclass
public class BaseModel {
    //    @Id
    private long id;
    //    @CreatedDate
    private Date createdAt;
    //    @LastModifiedDate
    private Date updatedAt;
}
