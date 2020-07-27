package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.service.IOrderService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.OrderStatus;
import co.edu.cedesistemas.common.model.Status;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class OrderController {

    private final IOrderService orderService;

    @PostMapping(value = "/orders", consumes = MediaType.APPLICATION_JSON_VALUE)
    @HystrixCommand(fallbackMethod = "createOrderFallback")
    public ResponseEntity<Status<?>> createOrder(@RequestBody Order order) {
        log.info("Creating the order...");
        try {
            order.setStatus(OrderStatus.CREATED);
            Order created = this.orderService.createOrder(order);
            log.info("The order was created successfully");
            addLinks(created);
            addSelfLink(created);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        } catch (Exception ex) {
            log.error("There was an error creating the order");
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orders/{id}")
    @HystrixCommand(fallbackMethod = "getOrderByIdFallback")
    public ResponseEntity<Status<?>> getOrderById(@PathVariable String id) {
        log.info("Getting the order by id");
        try {
            Optional<Order> order = Optional.ofNullable(this.orderService.getById(id));

            if (order.isPresent()) {
                addSelfLink(order.get());
                return DefaultResponseBuilder.defaultResponse(order.get(), HttpStatus.OK);
            }
            return DefaultResponseBuilder.errorResponse("Order not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            log.error("There was an error getting the order by id");
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orders/{id}/items")
    @HystrixCommand(fallbackMethod = "getOrderItemsFallback")
    public ResponseEntity<Status<?>> getOrderItems(@PathVariable String id) {
        log.info("Getting the order items...");
        try {
            Optional<Order> order = Optional.ofNullable(this.orderService.getById(id));

            if (order.isPresent()) {
                addSelfLink(order.get());
                return DefaultResponseBuilder.defaultResponse(order.get().getItems(), HttpStatus.OK);
            }
            return DefaultResponseBuilder.errorResponse("Order not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            log.error("There was an error getting the order items by id");
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<Status<?>> createOrderFallback(@RequestBody Order order) {
        log.error("Creating order fallback ");
        Status<?> status = Status.builder()
                .message("The service is unavailable. Please try again")
                .code(HttpStatus.SERVICE_UNAVAILABLE.value()).build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
    }

    private ResponseEntity<Status<?>> getOrderByIdFallback(@PathVariable String id) {
        log.error("Getting order by id fallback ");
        Status<?> status = Status.builder()
                .message("The service is unavailable. Please try again")
                .code(HttpStatus.SERVICE_UNAVAILABLE.value()).build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
    }

    private ResponseEntity<Status<?>> getOrderItemsFallback(@PathVariable String id) {
        log.error("Getting order items fallback ");
        Status<?> status = Status.builder()
                .message("The service is unavailable. Please try again")
                .code(HttpStatus.SERVICE_UNAVAILABLE.value()).build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
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
