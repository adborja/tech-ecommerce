package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.service.IUserService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
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

@RestController
@AllArgsConstructor
@Slf4j
public class UserController {
    private final IUserService service;

    @PostMapping("/users")
    public ResponseEntity<Status<?>> createUser(@RequestBody User user) {
        try {
            User created = service.createUser(user);
            addSelfLink(created);
            addLinks(created);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Status<?>> getUserById(@PathVariable String id) {
        try {
            User found = service.getById(id);
            if (found != null) return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            else return DefaultResponseBuilder.defaultResponse("user not found", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/by-email")
    public ResponseEntity<Status<?>> getUserByEmail(@RequestParam String email) {
        try {
            List<User> found = service.getByEmail(email);
            return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<Status<?>> updateUser(@PathVariable String id, @RequestBody User user) {
        try {
            User updated = service.updateUser(id, user);
            if (updated != null) return DefaultResponseBuilder.defaultResponse(updated, HttpStatus.OK);
            else return DefaultResponseBuilder.defaultResponse("user not found", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Status<?>> deleteUser(@PathVariable String id) {
        try {
            service.deleteUser(id);
            return DefaultResponseBuilder.defaultResponse("delete user id: "+id,HttpStatus.OK);
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
        Link byEmailLink = linkTo(methodOn(UserController.class)
                .getUserByEmail(user.getEmail()))
                .withRel("by-email")
                .withType("GET");
        user.add(byEmailLink);

        Link update = linkTo(methodOn(UserController.class)
                .updateUser(user.getId(), user))
                .withRel("update")
                .withMedia("application/json")
                .withType("PATCH");
        user.add(update);

        Link deleteLink = linkTo(methodOn(UserController.class)
                .deleteUser(user.getId()))
                .withRel("delete")
                .withType("DELETE");
        user.add(deleteLink);
    }



}