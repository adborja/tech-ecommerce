package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.service.AddressService;
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
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
@Slf4j
public class AdressController {

    private final IAddressService service;



    @PostMapping("/addresses")
    public ResponseEntity<Status<?>> createStore(@RequestBody Address address) {

        Address created = service.createAddress(address);
        addSelfLink(created);
        return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);

    }

    @GetMapping("/addresses/{id}")
    public ResponseEntity<Status<?>> getAddress(@PathVariable String id) {
        Optional<Address> address = service.getAddress(id);
        return DefaultResponseBuilder.defaultResponse(address, HttpStatus.OK);

    }

    private static void addSelfLink(@NotNull final Address address) {
        Link selfLink = linkTo(methodOn(StoreController.class)
                .getStoreById(address.getId()))
                .withSelfRel().withType("GET");
        address.add(selfLink);
    }
}
