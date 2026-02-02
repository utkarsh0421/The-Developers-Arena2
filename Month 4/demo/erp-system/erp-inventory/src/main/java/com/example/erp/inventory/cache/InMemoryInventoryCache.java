package com.example.erp.inventory.cache;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Primary
public class InMemoryInventoryCache {
    private final Map<Long, InventoryCache> store = new ConcurrentHashMap<>();

    public Optional<InventoryCache> get(Long sku) {
        return Optional.ofNullable(store.get(sku));
    }

    public void put(Long sku, int quantity) {
        store.put(sku, new InventoryCache(sku, quantity, LocalDateTime.now()));
    }

    public void evict(Long sku) {
        store.remove(sku);
    }
}
