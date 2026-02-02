package com.example.erp.inventory.dto;

public class InventoryUpdateResult {
    private Long productId;
    private int previousStock;
    private int updatedStock;
    private String status;

    public InventoryUpdateResult() {}

    public InventoryUpdateResult(Long productId, int previousStock, int updatedStock, String status) {
        this.productId = productId;
        this.previousStock = previousStock;
        this.updatedStock = updatedStock;
        this.status = status;
    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public int getPreviousStock() { return previousStock; }
    public void setPreviousStock(int previousStock) { this.previousStock = previousStock; }
    public int getUpdatedStock() { return updatedStock; }
    public void setUpdatedStock(int updatedStock) { this.updatedStock = updatedStock; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

