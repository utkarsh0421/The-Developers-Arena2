package com.example.erp.inventory.service;

import com.example.erp.inventory.cache.InMemoryInventoryCache;
import com.example.erp.inventory.dto.InventoryUpdateResult;
import com.example.erp.inventory.model.Product;
import com.example.erp.inventory.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class InventoryServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private InMemoryInventoryCache inventoryCache;

    @InjectMocks
    private com.example.erp.inventory.service.InventoryServiceImpl inventoryService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateInventorySuccess() throws Exception {
        Product p = new Product();
        p.setSku(100L);
        p.setName("Widget");
        p.setStockQuantity(10);
        p.setLastUpdated(LocalDateTime.now());

        when(productRepository.findAll()).thenReturn(Arrays.asList(p));
        when(productRepository.save(any(Product.class))).thenReturn(p);

        InventoryUpdateResult result = inventoryService.updateInventory(100L, 5).get();
        assertNotNull(result);
        assertEquals(15, result.getUpdatedStock());
        assertEquals(10, result.getPreviousStock());
    }

    @Test
    public void testUpdateInventoryInsufficient() {
        Product p = new Product();
        p.setSku(200L);
        p.setName("Gadget");
        p.setStockQuantity(2);
        p.setLastUpdated(LocalDateTime.now());

        when(productRepository.findAll()).thenReturn(Arrays.asList(p));

        assertThrows(RuntimeException.class, () -> {
            inventoryService.updateInventory(200L, -5).join();
        });
    }
}

