package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.commerce.service.AddressService;
import co.edu.cedesistemas.commerce.service.IAddressService;
import co.edu.cedesistemas.commerce.service.StoreService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class AddressController {
    private final IAddressService service;

    @PostMapping("/addresses")
    public ResponseEntity<Status<?>> createAddress(@RequestBody Address address) {
        try {
            Address created = service.createAddress(address);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/addresses/{id}")
    public ResponseEntity<Status<?>> getAddressById(@PathVariable String id) {
        try {
            Address found = service.getById(id);
            if (found != null) return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            else return DefaultResponseBuilder.errorResponse("address not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}