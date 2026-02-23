package com.example.starter.integration;

import com.example.starter.adapters.in.rest.dto.CreateProductRequest;
import com.example.starter.adapters.in.rest.dto.ProductResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
class ProductApiIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("starter")
            .withUsername("starter")
            .withPassword("starter");

    @Autowired
    private TestRestTemplate restTemplate;

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "none");
        registry.add("spring.liquibase.enabled", () -> true);
    }

    @Test
    void createThenGetProductShouldSucceed() {
        CreateProductRequest request = new CreateProductRequest("Keyboard", "Mechanical keyboard", BigDecimal.valueOf(89.90));

        ResponseEntity<ProductResponse> createResponse =
                restTemplate.postForEntity("/api/v1/products", request, ProductResponse.class);
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        assertNotNull(createResponse.getBody().id());
        assertNotNull(createResponse.getHeaders().getFirst("X-Correlation-ID"));

        ResponseEntity<ProductResponse> getResponse =
                restTemplate.getForEntity("/api/v1/products/" + createResponse.getBody().id(), ProductResponse.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals("Keyboard", getResponse.getBody().name());
    }
}
