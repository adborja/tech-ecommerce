package co.edu.cedesistemas.commerce.inventory.service;

import co.edu.cedesistemas.commerce.inventory.model.SKU;
import co.edu.cedesistemas.commerce.inventory.model.Warehouse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class InventoryService {
    private final WarehouseService warehouseService;
    private final ProductService productService;

    @Cacheable(value = "warehouse_items_cost", key = "#warehouseId")
    public Double getWarehouseItemsCost(final String warehouseId) {
        log.info("calculating warehouse cost ...");

        try {
            log.info("simulating long time operation");
            Thread.sleep(5000L);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        Set<Warehouse.Item> items = warehouseService.getWarehouseItems(warehouseId);
        return items.stream()
                .flatMap(i -> productService.getById(i.getId()).getSkus()
                        .stream()
                        .filter(s -> s.getSku().equals(i.getSku())))
                .mapToDouble(SKU::getPrice)
                .sum();
    }
}
