package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.service.OrderService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService service;



    @PostMapping("/orders")
    public ResponseEntity<Status<?>> createStore(@RequestBody Order order) {

        Order created = service.createOrder(order);
        return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);

    }

    @GetMapping("/orders/{id}/items")
    public ResponseEntity<Status<?>> getAddress(@PathVariable String id) {
        Optional<Order> order = service.getOrder(id);
        return DefaultResponseBuilder.defaultResponse(order, HttpStatus.OK);

    }

}
