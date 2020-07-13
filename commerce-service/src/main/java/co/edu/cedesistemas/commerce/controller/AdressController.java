package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.commerce.service.AddressService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
public class AdressController {

    private final AddressService service;



    @PostMapping("/addresses")
    public ResponseEntity<Status<?>> createStore(@RequestBody Address address) {

        Address created = service.createAddress(address);
        return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);

    }

    @GetMapping("/addresses/{id}")
    public ResponseEntity<Status<?>> getAddress(@PathVariable String id) {
        Optional<Address> address = service.getAddress(id);
        return DefaultResponseBuilder.defaultResponse(address, HttpStatus.OK);

    }

}
