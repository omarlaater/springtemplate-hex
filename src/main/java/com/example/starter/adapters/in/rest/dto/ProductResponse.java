package com.example.starter.adapters.in.rest.dto;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Long version
) {
}
