package com.example.starter.application.service;

import com.example.starter.domain.exception.BusinessValidationException;
import com.example.starter.domain.exception.ProductNotFoundException;
import com.example.starter.domain.model.Product;
import com.example.starter.ports.out.ProductRepositoryPort;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductApplicationServiceTest {

    @Mock
    private ProductRepositoryPort repositoryPort;

    private ProductApplicationService service;

    @BeforeEach
    void setUp() {
        service = new ProductApplicationService(repositoryPort, new SimpleMeterRegistry());
    }

    @Test
    void createShouldPersistWhenNameIsUnique() {
        when(repositoryPort.existsByNameIgnoreCase("Laptop")).thenReturn(false);
        when(repositoryPort.save(any(Product.class))).thenReturn(
                new Product(1L, "Laptop", "Dev laptop", BigDecimal.valueOf(1400), 0L)
        );

        var result = service.create("Laptop", "Dev laptop", BigDecimal.valueOf(1400));

        assertEquals(1L, result.id());
        assertEquals("Laptop", result.name());
        assertEquals(0L, result.version());
    }

    @Test
    void updateShouldFailWhenVersionMissing() {
        assertThrows(
                BusinessValidationException.class,
                () -> service.update(1L, "Laptop", "Desc", BigDecimal.TEN, null)
        );
    }

    @Test
    void findByIdShouldThrowWhenMissing() {
        when(repositoryPort.findById(44L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> service.findById(44L));
    }

    @Test
    void listShouldReturnPagedResult() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        when(repositoryPort.findByNameContaining(null, pageRequest)).thenReturn(
                new PageImpl<>(
                        List.of(new Product(1L, "Phone", "Smart phone", BigDecimal.valueOf(700), 0L)),
                        pageRequest,
                        1
                )
        );

        var result = service.list(null, pageRequest);
        assertEquals(1, result.getContent().size());
        assertEquals("Phone", result.getContent().get(0).name());
        assertEquals(1, result.getTotalElements());
    }
}
