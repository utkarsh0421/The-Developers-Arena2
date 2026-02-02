package com.example.erp.inventory.cache;

import java.time.LocalDateTime;

public record InventoryCache(Long sku, int quantity, LocalDateTime lastUpdated) {}
