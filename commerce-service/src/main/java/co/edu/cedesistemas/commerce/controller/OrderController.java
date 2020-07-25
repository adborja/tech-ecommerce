package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;
import co.edu.cedesistemas.commerce.service.IOrderService;
import co.edu.cedesistemas.commerce.service.OrderService;
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

@AllArgsConstructor
@RestController
@Slf4j
public class OrderController {

    private IOrderService service;

    @GetMapping("/orders/{id}")
    @HystrixCommand(fallbackMethod = "getFallback")
    public ResponseEntity<Status<?>> getOrder(@PathVariable String id){
        try{
            Order found = service.getOrderById(id);
            if (found != null) {
                addSelfLink(found);
                return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            }else return DefaultResponseBuilder.defaultResponse("order not found", HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orders/{id}/items")
    @HystrixCommand(fallbackMethod = "getFallback")
    public ResponseEntity<Status<?>> getItems(@PathVariable String id){
        try{
            List<OrderItem> found = service.getOrderItemsById(id);
            if (found != null) return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            else return DefaultResponseBuilder.defaultResponse("order not found", HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/orders")
    public ResponseEntity<Status<?>> createOrder(@RequestBody Order order){
        try{
            Order created = service.createOrder(order);
            addSelfLink(created);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<Status<?>> getFallback(final String id) {
        log.error("getting order or items by id fallback {}", id);
        Status<?> status = Status.builder()
                ._hits(1)
                .message("service unavailable. please try again")
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
    }

    private static void addSelfLink(@NotNull final Order order) {
        Link selfLink = linkTo(methodOn(OrderController.class)
                .getOrder(order.getId()))
                .withSelfRel().withType("GET");
        order.add(selfLink);

        Link delete = linkTo(methodOn(OrderController.class)
                .getItems(order.getId()))
                .withRel("items")
                .withType("GET");
        order.add(delete);
    }

}
