package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;
import co.edu.cedesistemas.commerce.service.IOrderService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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
    @HystrixCommand(fallbackMethod = "getByIdFallback")
    public ResponseEntity<Status<?>> getAddress(@PathVariable String id) {
        try {
            Order order = service.getById(id);
            if (order != null) {
                List<OrderItem> orderItems =  order.getItems();
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
    @HystrixCommand(fallbackMethod = "getByIdFallback")
    public ResponseEntity<Status<?>> getItems(@PathVariable String id) {
        try {
            Order order = service.getById(id);
            if(order!=null) {
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

    private ResponseEntity<Status<?>> getByIdFallback(final String id) {
        log.error("getting store by id fallback {}", id);
        Status<?> status = Status.builder()
                ._hits(1)
                .message("service unavailable. please try again")
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Status<?>> deleteOrderById(@PathVariable String id) {
        service.deleteOrder(id);
        return new ResponseEntity<>(Status.success(), HttpStatus.OK);
    }

}
