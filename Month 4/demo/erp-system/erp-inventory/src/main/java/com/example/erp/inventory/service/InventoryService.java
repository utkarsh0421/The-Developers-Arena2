package com.example.erp.inventory.service;

import com.example.erp.inventory.dto.InventoryUpdateResult;

import java.util.concurrent.CompletableFuture;

public interface InventoryService {
    CompletableFuture<InventoryUpdateResult> updateInventory(Long productId, int quantity);
    void generateLowStockReport();
}

