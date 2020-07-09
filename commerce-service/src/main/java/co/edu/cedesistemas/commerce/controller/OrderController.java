package co.edu.cedesistemas.commerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;
import co.edu.cedesistemas.commerce.service.OrderService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
public class OrderController {

	private final OrderService service;
	
	@PostMapping("/orders/")
	public ResponseEntity<Status<?>> createOrder(@RequestBody Order order){
		try {
			Order createdOrder = service.createOrder(order);
			return DefaultResponseBuilder.defaultResponse(createdOrder, HttpStatus.CREATED);
		} catch(Exception ex) {
			return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/orders/{id}/items")
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
	public ResponseEntity<Status<?>> getOrder(@PathVariable String id){
		try {
			Order found = service.getOrder(id);
			if(found != null) return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK); 
			else return DefaultResponseBuilder.errorResponse("Orden no encontrada", null, HttpStatus.NOT_FOUND);
		} catch(Exception ex) {
			return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}


