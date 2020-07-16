package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.commerce.service.IAddressService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@RestController
public class AddressController {

    IAddressService service;

    @PostMapping("/addresses")
    public ResponseEntity<Status<?>> createAddress(@RequestBody Address address){
        try{
            Address created = service.createAddress(address);
            addSelfLink(created);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/addresses/{id}")
    public  ResponseEntity<Status<?>> getAddress(@PathVariable String id){
        try{
            Address found = service.getAddressById(id);
            if (found != null){
                addSelfLink(found);
                return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            } else return DefaultResponseBuilder.errorResponse("Address not found", null, HttpStatus.NOT_FOUND);

        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static void addSelfLink(@NotNull final Address address) {
        Link selfLink = linkTo(methodOn(AddressController.class)
                .getAddress(address.getId()))
                .withSelfRel().withType("GET");
        address.add(selfLink);
    }


}
