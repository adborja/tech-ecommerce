package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;
import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.commerce.service.IOrderService;
import co.edu.cedesistemas.commerce.service.OrderService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

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
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Status<?>> getOrderById(@PathVariable String id) {
        try {
            Order found = service.getById(id);
            if (found != null) {
                addSelfLink(found);
                return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            }else return DefaultResponseBuilder.errorResponse("store not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orders/{id}/items")
    public ResponseEntity<Status<?>> getOrderItems(@PathVariable String id) {
        try {

            List<OrderItem> found = service.getOrderItems(id);
            if (found != null) return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            else return DefaultResponseBuilder.errorResponse("store not found", null, HttpStatus.NOT_FOUND);
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
                .getOrderItems(order.getId()))
                .withRel("by-type")
                .withType("GET");
        order.add(byTypeLink);

    }

}
