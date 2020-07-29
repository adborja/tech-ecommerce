package co.edu.cedesistemas.commerce.inventory.service;

import co.edu.cedesistemas.commerce.inventory.model.Warehouse;
import co.edu.cedesistemas.commerce.inventory.repository.WarehouseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class WarehouseService {
    private final WarehouseRepository repository;

    public Warehouse createWarehouse(final Warehouse warehouse) {
        warehouse.setId(UUID.randomUUID().toString());
        warehouse.setActive(true);
        return repository.save(warehouse);
    }

    public Set<Warehouse.Item> getWarehouseItems(final String warehouseId) {
        Warehouse warehouse = repository.findById(warehouseId).orElse(null);
        if (warehouse != null) {
            return warehouse.getItems();
        }
        return null;
    }

    @Cacheable(cacheNames = "warehouses", key = "#id")
    public Warehouse getWarehouseById(final String id, final String test) {
        log.info("getting warehouse from database ...");
        return repository.findById(id).orElse(null);
    }

    @CachePut(value = "warehouses", key = "#warehouse.id")
    public Warehouse updateWarehouse(final Warehouse warehouse) {
        log.info("updating warehouse to database ...");
        return repository.save(warehouse);
    }

    @CacheEvict(value = "warehouses", allEntries = true)
    public void deleteWarehouse(final String id) {
        log.info("deleting warehouse from database ...");
        repository.deleteById(id);
    }
}
