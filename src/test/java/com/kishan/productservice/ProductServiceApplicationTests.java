package com.kishan.productservice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceApplicationTests {

    @Test
    @DisplayName("Context load test")
    void contextLoads() {
        System.out.println("Context loaded successfully");
    }

}
