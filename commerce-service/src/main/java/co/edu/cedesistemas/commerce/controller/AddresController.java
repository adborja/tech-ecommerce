package co.edu.cedesistemas.commerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.service.IAddressService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
public class AddresController {

	private final IAddressService service;

	@GetMapping("/addresses/{id}")
	@HystrixCommand(fallbackMethod = "getAddressByIdFallback")
	public ResponseEntity<Status<?>> getAddressById(@PathVariable String id) {
		try {
			Address found = service.getById(id);
			if (found != null)
				return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
			else
				return DefaultResponseBuilder.errorResponse("No se pudo encontrar direccion con id " + id, null,
						HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/addresses")
	public ResponseEntity<Status<?>> createAddress(@RequestBody Address address) {
		try {
			Address created = service.createAddress(address);
			return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
		} catch (Exception ex) {
			return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private ResponseEntity<Status<?>> getAddressByIdFallback(final String id) {
		log.error("getting address by id fallback {}", id);
		Status<?> status = Status.builder()._hits(1).message("service unavailable. please try again")
				.code(HttpStatus.SERVICE_UNAVAILABLE.value()).build();
		return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
	}

}
