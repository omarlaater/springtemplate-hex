package com.example.starter.application.service;

import com.example.starter.domain.exception.BusinessValidationException;
import com.example.starter.domain.exception.ProductNotFoundException;
import com.example.starter.domain.model.Product;
import com.example.starter.ports.out.ProductRepositoryPort;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class ProductApplicationService {

    private final ProductRepositoryPort productRepositoryPort;
    private final Counter createCounter;
    private final Counter updateCounter;
    private final Timer listTimer;

    public ProductApplicationService(ProductRepositoryPort productRepositoryPort, MeterRegistry meterRegistry) {
        this.productRepositoryPort = productRepositoryPort;
        this.createCounter = meterRegistry.counter("products.created.total");
        this.updateCounter = meterRegistry.counter("products.updated.total");
        this.listTimer = meterRegistry.timer("products.list.duration");
    }

    public Product create(String name, String description, BigDecimal price) {
        validateCommand(name, price);
        if (productRepositoryPort.existsByNameIgnoreCase(name)) {
            throw new BusinessValidationException("Product name already exists");
        }

        Product saved = productRepositoryPort.save(new Product(
                null,
                name.trim(),
                description,
                price,
                null
        ));
        createCounter.increment();
        return saved;
    }

    public Product update(Long id, String name, String description, BigDecimal price, Long expectedVersion) {
        validateCommand(name, price);
        if (expectedVersion == null) {
            throw new BusinessValidationException("Version is required for updates");
        }

        Product current = productRepositoryPort.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        if (productRepositoryPort.existsByNameIgnoreCaseAndIdNot(name, id)) {
            throw new BusinessValidationException("Product name already exists");
        }

        Product updated = productRepositoryPort.save(new Product(
                current.id(),
                name.trim(),
                description,
                price,
                expectedVersion
        ));
        updateCounter.increment();
        return updated;
    }

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productRepositoryPort.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Page<Product> list(String nameContains, Pageable pageable) {
        Timer.Sample sample = Timer.start();
        Page<Product> page;
        try {
            page = productRepositoryPort.findByNameContaining(nameContains, pageable);
        } finally {
            sample.stop(listTimer);
        }
        if (page == null) {
            throw new IllegalStateException("Repository must return a page");
        }
        return page;
    }

    private void validateCommand(String name, BigDecimal price) {
        if (name == null || name.isBlank()) {
            throw new BusinessValidationException("Product name is required");
        }
        if (price == null || price.signum() < 0) {
            throw new BusinessValidationException("Price must be positive or zero");
        }
    }
}
