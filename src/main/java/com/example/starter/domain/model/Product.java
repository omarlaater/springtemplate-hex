package com.example.starter.domain.model;

import java.math.BigDecimal;

public record Product(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Long version
) {
    public Product {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (price == null || price.signum() < 0) {
            throw new IllegalArgumentException("Price must be positive or zero");
        }
    }
}
