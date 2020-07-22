package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/orders")
public class OrderController {

    private final IOrderService service;


    @PostMapping()
    public ResponseEntity<Status<?>> createOrder(@RequestBody Order order) {

        try {
            Order created = service.createOrder(order);
            addSelfLink(created);
            addLinks(created);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return DefaultResponseBuilder.errorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<Status<?>> getAddress(@PathVariable String id) {
        try {
            Optional<Order> order = service.getOrder(id);
            if (order.isPresent()) {
                List<OrderItem> orderItems =  order.get().getItems();
                return DefaultResponseBuilder.defaultResponse(orderItems, HttpStatus.OK);
            }
            else
                return DefaultResponseBuilder.errorResponse("Order not found", null, HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(),ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Status<?>> getItems(@PathVariable String id) {
        try {
            Optional<Order> order = service.getOrder(id);
            if(order.isPresent()) {

                return DefaultResponseBuilder.defaultResponse(order, HttpStatus.OK);
            }
            else
                return DefaultResponseBuilder.errorResponse("Order not found", null, HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(),ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private static void addSelfLink(@NotNull final Order order) {
        Link selfLink = linkTo(methodOn(StoreController.class)
                .getStoreById(order.getId()))
                .withSelfRel().withType("GET");
        order.add(selfLink);
    }

    private static void addLinks(@NotNull final Order order) {
        Link byTypeLink = linkTo(methodOn(OrderController.class)
                .getItems(order.getId()))
                .withRel("items")
                .withType("GET");
        order.add(byTypeLink);

    }

}
