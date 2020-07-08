package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.commerce.service.StoreService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class StoreController {
    private final StoreService service;

    @PostMapping("/stores")
    public ResponseEntity<Status<?>> createStore(@RequestBody Store store) {
        try {
            Store created = service.createStore(store);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/stores/{id}")
    public ResponseEntity<Status<?>> getStoreById(@PathVariable String id) {
        try {
            Store found = service.getById(id);
            if (found != null) return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            else return DefaultResponseBuilder.defaultResponse("store not found", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/stores/by-name")
    public ResponseEntity<Status<?>> getStoresByName(@RequestParam String name) {
        try {
            List<Store> found = service.getByName(name);
            return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/stores/by-type")
    public ResponseEntity<Status<?>> getStoresByType(@RequestParam Store.Type type) {
        try {
            List<Store> found = service.getByType(type);
            return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/stores/{id}")
    public ResponseEntity<Status<?>> updateStore(@PathVariable String id, @RequestBody Store store) {
        try {
            Store updated = service.updateStore(id, store);
            if (updated != null) return DefaultResponseBuilder.defaultResponse(updated, HttpStatus.OK);
            else return DefaultResponseBuilder.defaultResponse("store not found", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}