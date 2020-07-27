package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.service.IUserService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserController {

    private final IUserService userService;

    @PostMapping("/users")
    @HystrixCommand(fallbackMethod = "createUserFallback")
    public ResponseEntity<Status<?>> createUser(@RequestBody User user) {
        log.info("Creating user...");
        try {
            User created = this.userService.createUser(user);
            addSelfLink(created);
            addLinks(created);
            log.info("The user was created successfully");
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        } catch (Exception ex) {
            log.error("There was a problem creating the user");
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}")
    @HystrixCommand(fallbackMethod = "getUserByIdFallback")
    public ResponseEntity<Status<?>> getUserById(@PathVariable String id) {
        log.info("Getting the user by id");
        try {
            Optional<User> foundUser = this.userService.findById(id);
            if (foundUser.isPresent()) {
                addSelfLink(foundUser.get());
                return DefaultResponseBuilder.defaultResponse(foundUser.get(), HttpStatus.OK);
            }
            return DefaultResponseBuilder.defaultResponse("User not found", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            log.error("There was a problem getting the user");
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/by-email")
    @HystrixCommand(fallbackMethod = "getUsersByEmailFallback")
    public ResponseEntity<Status<?>> getUsersByEmail(@PathParam("email") String email) {
        log.info("Getting the user by email...");
        try {
            List<User> users = this.userService.findUsersByEmail(email);
            if (users.size() > 0) {
                users.forEach(UserController::addSelfLink);
                return DefaultResponseBuilder.defaultResponse(users, HttpStatus.OK);
            }
            return DefaultResponseBuilder.defaultResponse("Users not found", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            log.error("There was an error getting the user by email");
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/users/{id}")
    @HystrixCommand(fallbackMethod = "deleteUserFallback")
    public ResponseEntity<Status<?>> deleteUser(@PathVariable String id) {
        log.info("Deleting the user...");
        try {
            Optional<User> deletedUser = this.userService.deleteUserById(id);

            if (deletedUser.isPresent()) {
                addSelfLink(deletedUser.get());
                log.info("The user was deleted successfully");
                return DefaultResponseBuilder.defaultResponse(deletedUser.get(), HttpStatus.OK);
            }
            log.warn("The user couldn't be deleted");
            return DefaultResponseBuilder.defaultResponse("User no found", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            log.error("There was an error getting the user by email");
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/users/{id}")
    @HystrixCommand(fallbackMethod = "updateUserFallback")
    public ResponseEntity<Status<?>> updateUser(@PathVariable String id, @RequestBody User user) {
        log.info("Updating the user...");
        try {
            Optional<User> updatedUser = this.userService.updateUser(id, user);

            if (updatedUser.isPresent()) {
                addSelfLink(updatedUser.get());
                log.info("The user was updated successfully");
                return DefaultResponseBuilder.defaultResponse(updatedUser.get(), HttpStatus.OK);
            }
            log.warn("The user couldn't be updated");
            return DefaultResponseBuilder.defaultResponse("User no found", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            log.error("There was a problem updating the user");
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<Status<?>> createUserFallback(@RequestBody User user) {
        log.error("Creating user fallback {}");

        Status<?> status = Status.builder()
                .message("Service unavailable. Please try again")
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
    }

    private ResponseEntity<Status<?>> getUserByIdFallback(@RequestBody User user) {
        log.error("Getting user by id fallback {}");

        Status<?> status = Status.builder()
                .message("Service unavailable. Please try again")
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
    }

    private ResponseEntity<Status<?>> getUsersByEmailFallback(@RequestBody User user) {
        log.error("Getting user by email fallback {}");

        Status<?> status = Status.builder()
                .message("Service unavailable. Please try again")
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
    }

    private ResponseEntity<Status<?>> deleteUserFallback(@RequestBody User user) {
        log.error("Deleting user fallback {}");

        Status<?> status = Status.builder()
                .message("Service unavailable. Please try again")
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
    }

    private ResponseEntity<Status<?>> updateUserFallback(@RequestBody User user) {
        log.error("Updating user fallback {}");

        Status<?> status = Status.builder()
                .message("Service unavailable. Please try again")
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
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
