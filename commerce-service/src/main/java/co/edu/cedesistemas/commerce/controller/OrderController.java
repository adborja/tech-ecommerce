package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.service.IOrderService;
import co.edu.cedesistemas.commerce.service.OrderService;
import co.edu.cedesistemas.commerce.service.UserService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class OrderController {
    private final IOrderService service;

    @PostMapping("/orders")
    public ResponseEntity<Status<?>> createOrder(@RequestBody Order order) {
        try {
            Order created = service.createOrder(order);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Status<?>> getOrderById(@PathVariable String id) {
        try {
            Order found = service.getById(id);
            if (found != null) return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            else return DefaultResponseBuilder.errorResponse("order not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orders/{id}/items")
    public ResponseEntity<Status<?>> getUserByName(@PathVariable String id) {
        try {
            Order orderFound = service.getById(id);
            List itemsFound = orderFound.getItems();
            return DefaultResponseBuilder.defaultResponse(itemsFound, HttpStatus.OK);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}