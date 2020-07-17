package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;
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
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
@Slf4j
public class OrderController {
    private final IOrderService service;

    @PostMapping("/orders")
    public ResponseEntity<Status<?>> createOrder(@RequestBody Order order){
        try{
            Order created = service.createOrder(order);
            addSelfLink(created);
            addLinks(order);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        }catch (Exception e){
            return DefaultResponseBuilder.errorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Status<?>> getOrderById(@PathVariable String id){
        try{
            Order order = service.getById(id);
            addSelfLink(order);
            addLinks(order);
            return DefaultResponseBuilder.defaultResponse(order, HttpStatus.OK);
        }catch (Exception e){
            return DefaultResponseBuilder.errorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orders/{id}/items")
    public ResponseEntity<Status<?>> getOrderItems(@PathVariable String id){
        try{
            List<OrderItem> items = service.getItems(id);
            return DefaultResponseBuilder.defaultResponse(items, HttpStatus.OK);
        }catch (Exception e){
            return DefaultResponseBuilder.errorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static void addSelfLink(@NotNull final Order order){
        Link selfLink = linkTo(methodOn(OrderController.class).getOrderById(order.getId()))
                .withSelfRel().withType("GET");
        order.add(selfLink);
    }

    private static void addLinks(@NotNull final Order order){
        Link itemsLink = linkTo(methodOn(OrderController.class).getOrderItems(order.getId()))
                .withRel("items").withType("GET");
        order.add(itemsLink);
    }
}
