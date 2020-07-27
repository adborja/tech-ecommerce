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
            log.info("user created {} ",user);
            addSelfLink(created);
            addLinks(created);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}/activate")
    public ResponseEntity<Status<?>> changeStatus(@PathVariable String id) {
        try {
            User created = service.activateUser(id);
            log.info("user activated {} ",created);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.OK);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}")
    @HystrixCommand(fallbackMethod = "getUserByIdFallback")
    public ResponseEntity<Status<?>> getUserById(@PathVariable String id) {
        try {
            User found = service.getById(id);
            log.info("user found {} ",found);
            if (found != null) {
                return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            }else return DefaultResponseBuilder.errorResponse("user not found", null, HttpStatus.NOT_FOUND);
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

    private static void addLinks(@NotNull final User user) {
        Link activateLink = linkTo(methodOn(UserController.class)
                .changeStatus(user.getId()))
                .withRel("update")
                .withType("PUT");
        user.add(activateLink);
    }

    private ResponseEntity<Status<?>> createUserFallback(User user) {
        log.error("creating store fallback {}", user);
        Status<?> status = Status.builder()
                ._hits(1)
                .message("service unavailable. please try again. User name: "+user.getName())
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
    }

    private ResponseEntity<Status<?>> getUserByIdFallback(final String id) {
        log.error("getting user by id fallback {}", id);
        Status<?> status = Status.builder()
                ._hits(1)
                .message("service unavailable. please try again. User id "+id)
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
    }

}
