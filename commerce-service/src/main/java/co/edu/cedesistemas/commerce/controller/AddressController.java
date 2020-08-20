package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.service.IAddressService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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
    @HystrixCommand(fallbackMethod = "createAddressFallback")
    public ResponseEntity<Status<?>> createAddress(@RequestBody Address address) {
        try {
            Address created = service.createAddress(address);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/addresses/{id}")
    @HystrixCommand(fallbackMethod = "finAddressFallback", commandProperties = {@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "4")})
    public ResponseEntity<Status<?>> finAddress(@PathVariable String id){
        try {
            Address found = service.getById(id);
            if (found != null) {
                addSelfLink(found);
                return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            }
            else return DefaultResponseBuilder.errorResponse("Address not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static void addSelfLink(@NotNull final Address address) {
        Link selfLink = linkTo(methodOn(OrderController.class)
                .findOrderById(address.getId()))
                .withSelfRel().withType("GET");
        address.add(selfLink);
    }

    private ResponseEntity<Status<?>> finAddressFallback(final String id) {
        log.error("getting Address by id fallback {}", id);
        Status<?> status = Status.builder()
                ._hits(1)
                .message("service unavailable. getting Address by id fallback id "+id+", please try again")
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
    }

    private ResponseEntity<Status<?>> createAddressFallback(final Address address) {
        log.error("creating Address fallback {}", address.getName()+" - "+address.getCity());
        Status<?> status = Status.builder()
                ._hits(1)
                .message("service unavailable creating Address fallback {}, "+address.getName()+" - "+address.getCity()+" please try again")
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
