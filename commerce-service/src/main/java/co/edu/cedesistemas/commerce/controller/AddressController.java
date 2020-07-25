package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.service.AddressService;
import co.edu.cedesistemas.commerce.service.IAdressService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class AddressController {

    IAdressService service;

    @PostMapping("/addresses")
    public ResponseEntity<Status<?>> createAddress(@RequestBody Address address){
            Address newAddress = service.createAddress(address);
            return DefaultResponseBuilder.defaultResponse(newAddress, HttpStatus.CREATED);
         }


    @GetMapping("/addresses/{id}")
    public  ResponseEntity<Status<?>> getAddress(@PathVariable String id){
            Address address = service.getAddressById(id);
            try {
                return DefaultResponseBuilder.defaultResponse(address, HttpStatus.OK);
            } catch (Exception ex) {
                return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }



}