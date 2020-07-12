package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.service.UserService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<Status<?>> createUser(@RequestBody User user) {
        try {
            User created = this.userService.createUser(user);
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
            Optional<User> foundUser = this.userService.findById(id);
            if (foundUser.isPresent()) {
                addSelfLink(foundUser.get());
                return DefaultResponseBuilder.defaultResponse(foundUser.get(), HttpStatus.OK);
            }
            return DefaultResponseBuilder.defaultResponse("User not found", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/by-email")
    public ResponseEntity<Status<?>> getUsersByEmail(@PathParam("email") String email) {
        try {
            List<User> users = this.userService.findUsersByEmail(email);
            if (users.size() > 0) {
                users.forEach(UserController::addSelfLink);
                return DefaultResponseBuilder.defaultResponse(users, HttpStatus.OK);
            }
            return DefaultResponseBuilder.defaultResponse("Users not found", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Status<?>> deleteUser(@PathVariable String id) {
        try {
            Optional<User> deletedUser = this.userService.deleteUserById(id);

            if (deletedUser.isPresent()) {
                addSelfLink(deletedUser.get());
                return DefaultResponseBuilder.defaultResponse(deletedUser.get(), HttpStatus.OK);
            }
            return DefaultResponseBuilder.defaultResponse("User no found", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<Status<?>> updateUser(@PathVariable String id, @RequestBody User user) {
        try {
            Optional<User> updatedUser = this.userService.updateUser(id, user);

            if (updatedUser.isPresent()) {
                addSelfLink(updatedUser.get());
                return DefaultResponseBuilder.defaultResponse(updatedUser.get(), HttpStatus.OK);
            }
            return DefaultResponseBuilder.defaultResponse("User no found", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static void addSelfLink(@NotNull User user) {
        Link selfLink = linkTo(methodOn(UserController.class)
                .getUserById(user.getId()))
                .withSelfRel()
                .withType("GET");
        user.add(selfLink);
    }

    private static void addLinks(@NotNull User user) {
        Link linkByEmail = linkTo(methodOn(UserController.class)
                .getUsersByEmail(user.getName()))
                .withRel("by-email")
                .withType("GET");
        user.add(linkByEmail);

        Link linkUpdate = linkTo(methodOn(UserController.class)
                .updateUser(user.getId(), user))
                .withRel("update")
                .withMedia(MediaType.APPLICATION_JSON_VALUE)
                .withType("PATCH");
        user.add(linkUpdate);

        Link linkDeleteUser = linkTo(methodOn(UserController.class)
                .deleteUser(user.getId()))
                .withRel("delete")
                .withType("DELETE");
        user.add(linkDeleteUser);
    }


}
