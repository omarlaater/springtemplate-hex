package com.example.starter.adapters.in.rest.advice;

import com.example.starter.adapters.in.rest.dto.ApiErrorResponse;
import com.example.starter.adapters.in.rest.dto.FieldViolation;
import com.example.starter.domain.exception.BusinessValidationException;
import com.example.starter.domain.exception.ProductNotFoundException;
import jakarta.persistence.OptimisticLockException;
import org.slf4j.MDC;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        List<FieldViolation> violations = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::toViolation)
                .toList();
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "VALIDATION_ERROR",
                "Validation failed",
                violations
        );
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(ProductNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "NOT_FOUND", ex.getMessage(), Collections.emptyList());
    }

    @ExceptionHandler(BusinessValidationException.class)
    public ResponseEntity<ApiErrorResponse> handleBusinessValidation(BusinessValidationException ex) {
        return buildResponse(HttpStatus.UNPROCESSABLE_ENTITY, "BUSINESS_ERROR", ex.getMessage(), Collections.emptyList());
    }

    @ExceptionHandler({OptimisticLockingFailureException.class, OptimisticLockException.class})
    public ResponseEntity<ApiErrorResponse> handleConcurrency(Exception ex) {
        return buildResponse(
                HttpStatus.CONFLICT,
                "CONCURRENCY_CONFLICT",
                "Concurrent update detected. Reload resource and retry.",
                Collections.emptyList()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex) {
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_ERROR",
                "Unexpected error",
                Collections.emptyList()
        );
    }

    private FieldViolation toViolation(FieldError fieldError) {
        return new FieldViolation(fieldError.getField(), fieldError.getDefaultMessage());
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(HttpStatus status,
                                                           String code,
                                                           String message,
                                                           List<FieldViolation> violations) {
        ApiErrorResponse body = new ApiErrorResponse(
                code,
                message,
                MDC.get("correlationId"),
                Instant.now(),
                violations
        );
        return ResponseEntity.status(status).body(body);
    }
}
