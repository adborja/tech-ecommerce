package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.service.IOrderService;
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
public class OrderController {
    private final IOrderService service;

    @PostMapping("/orders")
    public ResponseEntity<Status<?>> createOrder(@RequestBody Order order) {
        try {
            Order created = service.createOrder(order);
            addSelfLink(created);
            addLinks(created);
            return DefaultResponseBuilder.defaultResponse(order, HttpStatus.CREATED);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Status<?>> getOrderById(@PathVariable String id) {
        try {
            Order found = service.getById(id);
            if (found != null) return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            else return DefaultResponseBuilder.defaultResponse("order not found", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orders/{id}/items")
    public ResponseEntity<Status<?>> getOrdersItemsById(@PathVariable String id) {
        try {
            Order found = service.getById(id);
            if (found != null) return DefaultResponseBuilder.defaultResponse(found.getItems(), HttpStatus.OK);
            else return DefaultResponseBuilder.defaultResponse("order not found", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static void addSelfLink(@NotNull final Order order) {
        Link selfLink = linkTo(methodOn(OrderController.class)
                .getOrderById(order.getId()))
                .withSelfRel().withType("GET");
        order.add(selfLink);
    }

    private static void addLinks(@NotNull final Order order) {
        Link byTypeLink = linkTo(methodOn(OrderController.class)
                .getOrdersItemsById(order.getId()))
                .withRel("items")
                .withType("GET");
        order.add(byTypeLink);

    }
    
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Status<?>> deleteOrderById(@PathVariable String id) {
        service.deleteOrder(id);
        return new ResponseEntity<>(Status.success(), HttpStatus.OK);
    }
}
}
