package co.edu.cedesistemas.commerce.registration.controller;

import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.commerce.registration.service.UserService;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserService service;

    @PostMapping("/users")
    @HystrixCommand(fallbackMethod = "createUserFallback")
    public ResponseEntity<Status<?>> createUser(@RequestBody User user) {
        try {
            User created = service.createUser(user);
            addSelfLink(created);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}")
    @HystrixCommand(fallbackMethod = "getUserByIdFallback")
    public ResponseEntity<Status<?>> getUserById(@PathVariable String id) {
        try {
            User found = service.getById(id);
            if (found != null) return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            else return DefaultResponseBuilder.errorResponse("User not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}/activate")
    public ResponseEntity<Status<?>> updateUser(@PathVariable String id) {
        try {
            User updated = service.updateUser(id);
            if (updated != null) return DefaultResponseBuilder.defaultResponse(updated, HttpStatus.OK);
            else return DefaultResponseBuilder.errorResponse("User not found", null, HttpStatus.NOT_FOUND);
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

    private ResponseEntity<Status<?>> getUserByIdFallback(final String id) {
        log.error("getting User by id fallback {}", id);
        Status<?> status = Status.builder()
                ._hits(1)
                .message("service unavailable. Registration getting User by id fallback id "+id+", please try again")
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
    }

    private ResponseEntity<Status<?>> createUserFallback(final User user) {
        log.error("creating User fallback {}", user.getName()+" - "+user.getLastName());
        Status<?> status = Status.builder()
                ._hits(1)
                .message("service unavailable. Registration creating User fallback, "+user.getName()+" - "+user.getLastName()+", please try again")
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
