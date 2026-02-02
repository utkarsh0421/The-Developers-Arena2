package com.example.erp.inventory.service;

import com.example.erp.inventory.cache.InventoryCache;
import com.example.erp.inventory.dto.InventoryUpdateResult;
import com.example.erp.inventory.model.Product;
import com.example.erp.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    @Async("erpTaskExecutor")
    public CompletableFuture<InventoryUpdateResult> updateInventory(Long productId, int quantity) {
        // optimistic update with find and save
        Product product = productRepository.findAll().stream()
                .filter(p -> p.getSku() != null && p.getSku().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found"));

        int currentStock = product.getStockQuantity();
        int updatedStock = currentStock + quantity;
        if (updatedStock < 0) {
            throw new RuntimeException("Insufficient stock");
        }

        product.setStockQuantity(updatedStock);
        product.setLastUpdated(LocalDateTime.now());
        product.setVersion(product.getVersion() == null ? 1L : product.getVersion() + 1);
        Product saved = productRepository.save(product);

        // update cache via MongoTemplate stub (Redis adapter to be added later)
        InventoryCache cache = new InventoryCache(saved.getSku(), saved.getStockQuantity(), saved.getLastUpdated());
        // TODO: write to Redis

        // publish event TODO

        return CompletableFuture.completedFuture(new InventoryUpdateResult(saved.getSku(), currentStock, updatedStock, "SUCCESS"));
    }

    @Override
    @Scheduled(cron = "0 0 2 * * ?")
    public void generateLowStockReport() {
        List<Product> lowStock = productRepository.findLowStockProducts(10);
        if (!lowStock.isEmpty()) {
            // TODO: generate and persist report
            System.out.println("Low stock products: " + lowStock.size());
        }
    }
}

