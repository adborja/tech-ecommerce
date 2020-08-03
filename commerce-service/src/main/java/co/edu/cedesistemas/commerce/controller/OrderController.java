
package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;
import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.commerce.service.OrderService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
public class OrderController {

    private OrderService service;

    @GetMapping("/orders/{id}")
    public ResponseEntity<Status<?>> getOrderById(@PathVariable String id) {
        try {
            Order found = service.getById(id);
            addSelfLink(found);
            addLinks(found);
            if (found != null) return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            else return DefaultResponseBuilder.errorResponse("store not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/orders")
    public ResponseEntity<Status<?>> createOrder(@RequestBody Order order) {
        try {
            Order created = service.createOrder(order);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orders/{id}/items")
    public ResponseEntity<Status<?>> getOrderItemsById(@PathVariable String id) {
        try {
            List<OrderItem> found = service.getById(id).getItems();
            if (found.isEmpty()) return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
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
                .getOrderItemsById(order.getId()))
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
