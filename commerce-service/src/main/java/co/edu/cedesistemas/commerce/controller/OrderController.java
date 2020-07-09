package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;
import co.edu.cedesistemas.commerce.service.OrderService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Status<?>> createOrder(@RequestBody Order order){
        try{
            return DefaultResponseBuilder.defaultResponse(orderService.createOrder(order), HttpStatus.CREATED);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Status<?>> getOrderById(@PathVariable String orderId){
        try{
            Order order = orderService.getOrderById(orderId);
            if(order != null){
                return DefaultResponseBuilder.defaultResponse(order, HttpStatus.OK);
            }else {
                return DefaultResponseBuilder.errorResponse("Order Not Found", null, HttpStatus.NOT_FOUND);
            }
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{orderId}/items")
    public ResponseEntity<Status<?>> getOrdeItemsByOrderId(@PathVariable String orderId){
        try{
            Order order = orderService.getOrderById(orderId);
            if(order != null){
                return DefaultResponseBuilder.defaultResponse(order.getItems(), HttpStatus.OK);
            }else {
                return DefaultResponseBuilder.errorResponse("Order not found", null, HttpStatus.NOT_FOUND);
            }
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
