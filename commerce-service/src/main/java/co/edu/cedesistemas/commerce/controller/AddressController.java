package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.service.IAddressService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/addresses")
public class AddressController {
    private final IAddressService addressService;

    @PostMapping
    public ResponseEntity<Status<?>> createAddress(@RequestBody Address address){
        try {
            return DefaultResponseBuilder.defaultResponse(this.addressService.create(address), HttpStatus.CREATED);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<Status<?>> getAddressById(@PathVariable String addressId){
        try {
            Address address =  this.addressService.getById(addressId);
            if(address != null){
                return DefaultResponseBuilder.defaultResponse(address, HttpStatus.OK);
            }else{
                return DefaultResponseBuilder.errorResponse("Address not found", null, HttpStatus.NOT_FOUND);
            }
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
