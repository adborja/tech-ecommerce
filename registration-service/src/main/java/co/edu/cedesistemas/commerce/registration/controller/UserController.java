package co.edu.cedesistemas.commerce.registration.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import javax.validation.constraints.NotNull;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.commerce.registration.service.UserService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {
	
	private final UserService service;
	
	@GetMapping("/users/{id}")
    public ResponseEntity<Status<?>> getUserById(@PathVariable String id) {        
        try {
            User found = service.getById(id);
            addSelfLink(found);
            if (found != null) {
                return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            } else return DefaultResponseBuilder.errorResponse("user not found", null, HttpStatus.NOT_FOUND); 
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@PostMapping("/users")
	public ResponseEntity<Status<?>> createUser(@RequestBody User user){
		try {
            User created = service.createUser(user);
            addSelfLink(created);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
	@PutMapping("/users/{id}/{active}")
	public ResponseEntity<Status<?>> updateUserStatus(@PathVariable String id, @PathVariable String active) {
        try {
            User updated = service.updateUserStatus(id, active);
            if (updated != null) {
                return DefaultResponseBuilder.defaultResponse(updated, HttpStatus.OK);
            } else return DefaultResponseBuilder.errorResponse("user not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	
	
	 private static void addSelfLink(@NotNull final User user) {
	        Link selfLink = linkTo(methodOn(UserController.class)
	                .getUserById(user.getId()))
	                .withSelfRel().withType("GET");
	        user.add(selfLink);
	    }
	 

}
