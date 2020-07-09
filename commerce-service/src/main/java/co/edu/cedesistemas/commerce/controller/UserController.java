package co.edu.cedesistemas.commerce.controller;

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

import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.service.UserService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {
	
	private final UserService service;

	@PostMapping("/users")
	public ResponseEntity<Status<?>> createUser(@RequestBody User user) {
		try {
			User created = service.createUser(user);
			return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
		} catch (Exception ex) {
			return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<Status<?>> getUserById(@PathVariable String id) {
		try {
			User found = service.getById(id);
			if (found != null)
				return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
			else
				return DefaultResponseBuilder.errorResponse("User not found", null, HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/users/by-email")
	public ResponseEntity<Status<?>> getUsersByEmail(@RequestParam String email) {
		try {
			List<User> found = service.getByEmail(email);
			return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
		} catch (Exception ex) {
			return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("/users/{id}")
	public ResponseEntity<Status<?>> updateUser(@PathVariable String id, @RequestBody User User) {
		try {
			User updated = service.updateUser(id, User);
			if (updated != null)
				return DefaultResponseBuilder.defaultResponse(updated, HttpStatus.OK);
			else
				return DefaultResponseBuilder.errorResponse("User not found", null, HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Status<?>> deleteUser(@PathVariable String id){
		try {
			User found = service.getById(id);
			if (found == null) 
				return DefaultResponseBuilder.errorResponse("El usuario a eliminar no existe.", null, HttpStatus.NOT_FOUND);
			service.deleteUser(id);
			return DefaultResponseBuilder.defaultResponse(null, HttpStatus.OK);
		}catch (Exception e) {
			return DefaultResponseBuilder.errorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
