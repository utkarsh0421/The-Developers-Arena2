package com.example.erp.inventory.model;

import com.example.erp.core.model.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "products")
public class Product extends BaseEntity {
    private Long sku;
    private String name;
    private int stockQuantity;
    private LocalDateTime lastUpdated;

    public Long getSku() { return sku; }
    public void setSku(Long sku) { this.sku = sku; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
}

