package co.edu.cedesistemas.commerce.inventory.controller;

import co.edu.cedesistemas.commerce.inventory.model.Warehouse;
import co.edu.cedesistemas.commerce.inventory.service.WarehouseService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class WarehouseController {
    private final WarehouseService service;

    @PostMapping("/warehouses")
    public ResponseEntity<Status<?>> createWarehouse(@RequestBody final Warehouse warehouse) {
        Warehouse created = service.createWarehouse(warehouse);
        return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
    }
}
