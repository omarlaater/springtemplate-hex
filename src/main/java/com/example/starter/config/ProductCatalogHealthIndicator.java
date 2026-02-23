package com.example.starter.config;

import com.example.starter.ports.out.ProductRepositoryPort;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("catalog")
public class ProductCatalogHealthIndicator implements HealthIndicator {

    private final ProductRepositoryPort repositoryPort;

    public ProductCatalogHealthIndicator(ProductRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public Health health() {
        try {
            long totalProducts = repositoryPort.count();
            return Health.up()
                    .withDetail("totalProducts", totalProducts)
                    .build();
        } catch (Exception ex) {
            return Health.down(ex).build();
        }
    }
}
