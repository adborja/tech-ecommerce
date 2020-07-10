package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.service.OrderService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @PostMapping(value = "/orders", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Status<?>> createOrder(Order order) {
        try {
            Order created = this.orderService.createOrder(order);
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
                return DefaultResponseBuilder.defaultResponse(order, HttpStatus.OK);
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
                return DefaultResponseBuilder.defaultResponse(order.get().getItems(), HttpStatus.OK);
            }
            return DefaultResponseBuilder.errorResponse("Order not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
