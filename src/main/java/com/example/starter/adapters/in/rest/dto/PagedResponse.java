package com.example.starter.adapters.in.rest.dto;

import java.util.List;

public record PagedResponse<T>(
        List<T> items,
        long totalItems,
        int page,
        int size
) {
    public PagedResponse {
        items = items == null ? List.of() : List.copyOf(items);
    }
}
