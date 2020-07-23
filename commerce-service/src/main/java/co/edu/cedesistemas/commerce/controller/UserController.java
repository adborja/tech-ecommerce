package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.service.IUserService;
import co.edu.cedesistemas.commerce.service.ProductService;
import co.edu.cedesistemas.commerce.service.UserService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
            if (found != null){
                addLinks(found);
                return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            }else{
                return DefaultResponseBuilder.errorResponse("product not found", null, HttpStatus.NOT_FOUND);
            }


        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/by-email")
    public ResponseEntity<Status<?>> getUserByEmail(@RequestParam String email) {
        try {
            List<User> found = service.getByEmail(email);
            found.forEach(UserController::addLinks);
            return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Status<?>> deleteUserById(@PathVariable String id) {
        try {
            service.deleteUserById(id);
            return DefaultResponseBuilder.defaultResponse("User Deleted",HttpStatus.OK);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<Status<?>> updateUser(@PathVariable String id, @RequestBody User user) {
        try {
            if (!id.equalsIgnoreCase(user.getId()) && user.getId() != null){
                return DefaultResponseBuilder.errorResponse("Bad Request",null,HttpStatus.BAD_REQUEST);
            }else {
                User updatedUser = service.updateUser(id, user);
                if (updatedUser != null)
                    return DefaultResponseBuilder.defaultResponse(updatedUser, HttpStatus.OK);
                else
                    return DefaultResponseBuilder.errorResponse("user not found", null, HttpStatus.NOT_FOUND);
            }

        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.BAD_REQUEST);
        }
    }

    private static void addSelfLink(@NotNull final User user) {
        Link selfLink = linkTo(methodOn(UserController.class)
                .getUserById(user.getId()))
                .withSelfRel().withType("GET");
        user.add(selfLink);

    }

    private static void addLinks(@NotNull final User user) {

        Link byNameLink = linkTo(methodOn(UserController.class)
                .getUserByEmail(user.getEmail()))
                .withRel("by-email")
                .withType("GET");
        user.add(byNameLink);

        Link update = linkTo(methodOn(UserController.class)
                .updateUser(user.getId(), user))
                .withRel("update")
                .withMedia(MediaType.APPLICATION_JSON_VALUE)
                .withType("PATCH");
        user.add(update);

        Link selfDelLink = linkTo(methodOn(UserController.class)
                .deleteUserById(user.getId()))
                .withSelfRel().withType("DELETE");
        user.add(selfDelLink);
    }


}