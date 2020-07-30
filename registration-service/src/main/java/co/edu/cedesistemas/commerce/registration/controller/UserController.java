package co.edu.cedesistemas.commerce.registration.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import javax.validation.constraints.NotNull;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.commerce.registration.service.UserService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
public class UserController {
	
	private final UserService service;
	
	@GetMapping("/users/{id}")
	@HystrixCommand(fallbackMethod = "getUserByIdFallback")
    public ResponseEntity<Status<?>> getUserById(@PathVariable String id) {        
        try {
    		log.info("Attempt to get user {}",id);
            User found = service.getById(id);
            if (found != null) {
            	addSelfLink(found);
                return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            } else return DefaultResponseBuilder.defaultResponse("user not found", HttpStatus.NOT_FOUND); 
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@PostMapping("/users")
	public ResponseEntity<Status<?>> createUser(@RequestBody User user){
		try {
    		log.info("Attempt to create user");
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
        	log.info("Attempt to update user {} with status {}",id, active);
            User updated = service.updateUserStatus(id, active);
            if (updated != null) {
                return DefaultResponseBuilder.defaultResponse(updated, HttpStatus.OK);
            } else return DefaultResponseBuilder.errorResponse("user not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@DeleteMapping("/users/{id}")
    public ResponseEntity<Status<?>> deleteUser(@PathVariable String id) {
        service.deleteUser(id);
        return new ResponseEntity<>(Status.success(), HttpStatus.OK);
    }
	
	
	 private static void addSelfLink(@NotNull final User user) {
	        Link selfLink = linkTo(methodOn(UserController.class)
	                .getUserById(user.getId()))
	                .withSelfRel().withType("GET");
	        user.add(selfLink);
	    }
	 
	 public ResponseEntity<Status<?>> getUserByIdFallback(final String id){
		 log.error("Getting user by id fallback {}", id);
		 Status<?> status = Status.builder()
				 ._hits(1)
				 .message("service unavailable. please try again later")
				 .code(HttpStatus.SERVICE_UNAVAILABLE.value())
				 .build();
		 
		 return new ResponseEntity<>(status,HttpStatus.SERVICE_UNAVAILABLE);
	 }

}
