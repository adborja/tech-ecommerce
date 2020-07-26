package co.edu.cedesistemas.commerce.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.service.IProductService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
public class ProductController {

	private final IProductService service;

	@PostMapping("/products")
	public ResponseEntity<Status<?>> createProduct(@RequestBody Product product) {
		try {
			Product created = service.createProduct(product);
			return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
		} catch (Exception ex) {
			return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/products/{id}")
	@HystrixCommand(fallbackMethod = "getProductByIdFallback")
	public ResponseEntity<Status<?>> getProductById(@PathVariable String id) {
		try {
			Product found = service.getById(id);
			if (found != null)
				return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
			else
				return DefaultResponseBuilder.errorResponse("product not found", null, HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/products/by-name")
	@HystrixCommand(fallbackMethod = "getProductsByNameFallback")
	public ResponseEntity<Status<?>> getProductsByName(@RequestParam String name) {
		try {
			List<Product> found = service.getByName(name);
			return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
		} catch (Exception ex) {
			return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("/products/{id}")
	public ResponseEntity<Status<?>> updateProduct(@PathVariable String id, @RequestBody Product product) {
		try {
			String[] nullProps = Utils.getNullPropertyNames(product);

			if (Arrays.binarySearch(nullProps, "id") < 0)
				throw new Exception("The id property of product cannot be updated.");

			Product updated = service.updateProduct(id, product);
			if (updated != null)
				return DefaultResponseBuilder.defaultResponse(updated, HttpStatus.OK);
			else
				return DefaultResponseBuilder.errorResponse("product not found", null, HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/products/{id}")
	public ResponseEntity<Status<?>> deleteProduct(@PathVariable String id) {
		try {
			Product found = service.getById(id);
			if (found == null)
				return DefaultResponseBuilder.errorResponse("El producto a eliminar no existe.", null,
						HttpStatus.NOT_FOUND);
			service.deleteProduct(id);
			return DefaultResponseBuilder.defaultResponse("Producto eliminado correctamente", HttpStatus.OK);
		} catch (Exception e) {
			return DefaultResponseBuilder.errorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private ResponseEntity<Status<?>> getProductByIdFallback(final String id) {
		log.error("getting product by id fallback {}", id);
		Status<?> status = Status.builder()._hits(1).message("service unavailable. please try again")
				.code(HttpStatus.SERVICE_UNAVAILABLE.value()).build();
		return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
	}

	private ResponseEntity<Status<?>> getProductsByNameFallback(final String name) {
		log.error("getting product by name fallback {}", name);
		Status<?> status = Status.builder()._hits(1).message("service unavailable. please try again")
				.code(HttpStatus.SERVICE_UNAVAILABLE.value()).build();
		return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
	}

}
