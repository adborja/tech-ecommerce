package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.service.OrderService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping("/orders")
    public ResponseEntity<Status<?>> createOrder(@RequestBody Order order) {
        Order created = service.createOrder(order);
        return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Status<?>> getById(@PathVariable String id) {
        Order found = service.getById(id);
        if (found != null) return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
        return DefaultResponseBuilder.errorResponse("order not found", null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Status<?>> deleteOrderById(@PathVariable String id) {
        service.deleteOrder(id);
        return new ResponseEntity<>(Status.success(), HttpStatus.OK);
    }
}
