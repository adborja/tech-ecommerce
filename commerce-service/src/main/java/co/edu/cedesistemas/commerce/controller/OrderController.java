package co.edu.cedesistemas.commerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.service.IOrderService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
public class OrderController {

	private final IOrderService service;
	
	@PostMapping("/orders")
	public ResponseEntity<Status<?>> createOrder(@RequestBody Order order){
		try {
			Order createdOrder = service.createOrder(order);
			return DefaultResponseBuilder.defaultResponse(createdOrder, HttpStatus.CREATED);
		} catch(Exception ex) {
			return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/orders/{id}/items")
	@HystrixCommand(fallbackMethod = "getOrderItemsFallback")
	public ResponseEntity<Status<?>> getOrderItems(@PathVariable String id){
		try {
			Order found = service.getOrder(id);
			if(found != null) return DefaultResponseBuilder.defaultResponse(found.getItems(), HttpStatus.OK); 
			else return DefaultResponseBuilder.errorResponse("Orden no encontrada", null, HttpStatus.NOT_FOUND);
		} catch(Exception ex) {
			return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/orders/{id}")
	@HystrixCommand(fallbackMethod = "getOrderFallback")
	public ResponseEntity<Status<?>> getOrder(@PathVariable String id){
		try {
			Order found = service.getOrder(id);
			if(found != null) return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK); 
			else return DefaultResponseBuilder.errorResponse("Orden no encontrada", null, HttpStatus.NOT_FOUND);
		} catch(Exception ex) {
			return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/orders/{id}")
	public ResponseEntity<Status<?>> deleteOrder(@PathVariable String id){
		try {
			service.deleteOrder(id);
			return DefaultResponseBuilder.defaultResponse("order deleted", HttpStatus.OK);
		}catch (Exception ex) {
			return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private ResponseEntity<Status<?>> getOrderItemsFallback(final String id) {
		log.error("getting order items by id fallback {}", id);
		Status<?> status = Status.builder()._hits(1).message("service unavailable. please try again")
				.code(HttpStatus.SERVICE_UNAVAILABLE.value()).build();
		return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	private ResponseEntity<Status<?>> getOrderFallback(final String id) {
		log.error("getting order by id fallback {}", id);
		Status<?> status = Status.builder()._hits(1).message("service unavailable. please try again")
				.code(HttpStatus.SERVICE_UNAVAILABLE.value()).build();
		return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
	}
}


