package co.edu.cedesistemas.commerce.inventory.controller;

import co.edu.cedesistemas.commerce.inventory.service.InventoryService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class InventoryController {
    private final InventoryService service;

    @GetMapping("/inventories/warehouses/{id}/value")
    public ResponseEntity<Status<?>> getWarehouseValue(@PathVariable final String id) {
        Double d = service.getWarehouseItemsCost(id);
        return DefaultResponseBuilder.defaultResponse(d, HttpStatus.OK);
    }
}
