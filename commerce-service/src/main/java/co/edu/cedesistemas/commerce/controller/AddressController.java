package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.service.IAddressService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
@Slf4j
public class AddressController {
    private final IAddressService service;

    @PostMapping("/addresses")
    public ResponseEntity<Status<?>> createAddress(@RequestBody Address address){
        try{
            Address created = service.createAddress(address);
            addSelfLink(created);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        }catch (Exception e){
            return DefaultResponseBuilder.errorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/addresses/{id}")
    public ResponseEntity<Status<?>> getAddress(@PathVariable String id){
        try{
            Address address = service.getById(id);
            addSelfLink(address);
            return DefaultResponseBuilder.defaultResponse(address, HttpStatus.OK);
        }catch (Exception e){
            return DefaultResponseBuilder.errorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static void addSelfLink(@NotNull final Address address){
        Link selfLink = linkTo(methodOn(AddressController.class).getAddress(address.getId()))
                .withSelfRel().withType("GET");
        address.add(selfLink);
    }
}
