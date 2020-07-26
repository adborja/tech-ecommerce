package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.service.IAddressService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
@Slf4j
public class AddressesController {

    private final IAddressService addressService;

    @PostMapping(value = "/addresses", consumes = MediaType.APPLICATION_JSON_VALUE)
    @HystrixCommand(fallbackMethod = "createAddressFallback")
    public ResponseEntity<Status<?>> createAddress(@RequestBody Address address) {
        log.info("Creating address...");
        try {
            Address created = this.addressService.createAddress(address);
            addSelfLink(created);
            log.info("The address was created successfully");
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        } catch (Exception ex) {
            log.error("There was an error creating the address");
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/addresses/{id}")
    @HystrixCommand(fallbackMethod = "getAddressByIdFallback")
    public ResponseEntity<Status<?>> getAddressById(@PathVariable String id) {
        log.info("Getting address by id...");
        try {
            Optional<Address> address = this.addressService.findById(id);
            if (address.isPresent()) {
                addSelfLink(address.get());
                return DefaultResponseBuilder.defaultResponse(address.get(), HttpStatus.OK);
            }
            return DefaultResponseBuilder.errorResponse("Address not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            log.error("There was an error getting the address by id");
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<Status<?>> createAddressFallback(@RequestBody Address address) {
        log.error("Creating address fallback {}");
        Status<?> status = Status.builder()
                .message("Service unavailable. Please try again")
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
    }

    private ResponseEntity<Status<?>> getAddressByIdFallback(@PathVariable String id) {
        Status<?> status = Status.builder()
                .message("Service unavailable. Please try again")
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
    }

    private static void addSelfLink(@NotNull Address address) {
        Link selfLink = linkTo(methodOn(AddressesController.class)
                .getAddressById(address.getId()))
                .withSelfRel()
                .withType("GET");
        address.add(selfLink);
    }

}
