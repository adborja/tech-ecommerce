package co.edu.cedesistemas.commerce.registration.controller;

import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.commerce.registration.service.UserService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
@Slf4j
public class UserController {

    private UserService userService;

    @GetMapping("/users/{id}")
    public ResponseEntity<Status<?>> getUserById(@PathVariable String id) {
        log.info("Get users by id");
        try {
            User userFound = this.userService.findById(id);
            if (userFound != null) {
                return DefaultResponseBuilder.defaultResponse(userFound, HttpStatus.OK);
            }
            return DefaultResponseBuilder.errorResponse("Users not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            log.error("An error occurred when getUserById service was invoked ");
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<Status<?>> createUser(@RequestBody User user) {
        log.info("Creating user...");
        try {
            user.setStatus(User.Status.INACTIVE);
            User created = this.userService.createUser(user);
            addSelfLink(created);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        } catch (Exception ex) {
            log.error("An error occurred when createUser service was invoked ");
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}/activate")
    public ResponseEntity<Status<?>> updateUser(@PathVariable String id) {
        log.info("Activating user...");
        try {
            User foundUser = this.userService.findById(id);
            if (foundUser != null) {
                foundUser.setStatus(User.Status.ACTIVE);
                User updated = this.userService.updateUser(foundUser);
                return DefaultResponseBuilder.defaultResponse(updated, HttpStatus.OK);
            }
            return DefaultResponseBuilder.errorResponse("user not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void addSelfLink(User user) {
        Link selfLink = linkTo(methodOn(UserController.class)
                .getUserById(user.getId()))
                .withSelfRel()
                .withType("GET");
        user.add(selfLink);
    }

}
