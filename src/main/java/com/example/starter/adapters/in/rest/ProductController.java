package com.example.starter.adapters.in.rest;

import com.example.starter.adapters.in.rest.dto.CreateProductRequest;
import com.example.starter.adapters.in.rest.dto.PagedResponse;
import com.example.starter.adapters.in.rest.dto.ProductResponse;
import com.example.starter.adapters.in.rest.dto.UpdateProductRequest;
import com.example.starter.application.service.ProductApplicationService;
import com.example.starter.domain.model.Product;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductApplicationService productService;

    public ProductController(ProductApplicationService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@Valid @RequestBody CreateProductRequest request) {
        Product created = productService.create(request.name(), request.description(), request.price());
        return toResponse(created);
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id, @Valid @RequestBody UpdateProductRequest request) {
        Product updated = productService.update(id, request.name(), request.description(), request.price(), request.version());
        return toResponse(updated);
    }

    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return toResponse(product);
    }

    @GetMapping
    public PagedResponse<ProductResponse> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(required = false) String name
    ) {
        Sort.Direction sortDirection = "DESC".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<Product> result = productService.list(name, pageRequest);

        return new PagedResponse<>(
                result.getContent().stream().map(this::toResponse).toList(),
                result.getTotalElements(),
                result.getNumber(),
                result.getSize()
        );
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.id(),
                product.name(),
                product.description(),
                product.price(),
                product.version()
        );
    }
}
