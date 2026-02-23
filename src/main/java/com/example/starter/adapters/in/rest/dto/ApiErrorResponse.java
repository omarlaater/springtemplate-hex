package com.example.starter.adapters.in.rest.dto;

import java.time.Instant;
import java.util.List;

public record ApiErrorResponse(
        String code,
        String message,
        String correlationId,
        Instant timestamp,
        List<FieldViolation> violations
) {
    public ApiErrorResponse {
        violations = violations == null ? List.of() : List.copyOf(violations);
    }
}
