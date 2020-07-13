package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.service.IOrderService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
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
public class OrderController {

    private final IOrderService orderService;

    @PostMapping(value = "/orders", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Status<?>> createOrder(@RequestBody Order order) {
        try {
            order.setStatus(Order.Status.CREATED);
            Order created = this.orderService.createOrder(order);
            addLinks(created);
            addSelfLink(created);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Status<?>> getOrderById(@PathVariable String id) {
        try {
            Optional<Order> order = this.orderService.findById(id);

            if (order.isPresent()) {
                addSelfLink(order.get());
                return DefaultResponseBuilder.defaultResponse(order.get(), HttpStatus.OK);
            }
            return DefaultResponseBuilder.errorResponse("Order not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orders/{id}/items")
    public ResponseEntity<Status<?>> getOrderItems(@PathVariable String id) {
        try {
            Optional<Order> order = this.orderService.findById(id);

            if (order.isPresent()) {
                addSelfLink(order.get());
                return DefaultResponseBuilder.defaultResponse(order.get().getItems(), HttpStatus.OK);
            }
            return DefaultResponseBuilder.errorResponse("Order not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static void addSelfLink(@NotNull Order order) {
        Link selfLink = linkTo(methodOn(OrderController.class)
                .getOrderById(order.getId()))
                .withSelfRel()
                .withType("GET");
        order.add(selfLink);
    }

    private static void addLinks(@NotNull Order order) {
        Link linkItems = linkTo(methodOn(OrderController.class)
                .getOrderItems(order.getId()))
                .withRel("items")
                .withType("GET");
        order.add(linkItems);
    }
}
