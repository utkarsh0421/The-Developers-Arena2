package com.example.erp.inventory.cache;

import java.time.LocalDateTime;

public class InventoryCache {
    private Long productId;
    private int stock;
    private LocalDateTime lastUpdated;

    public InventoryCache() {}
    public InventoryCache(Long productId, int stock, LocalDateTime lastUpdated) {
        this.productId = productId;
        this.stock = stock;
        this.lastUpdated = lastUpdated;
    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
}

