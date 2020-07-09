package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.service.UserService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Status<?>> createUser(@RequestBody User user){
        try {
            return DefaultResponseBuilder
                    .defaultResponse(this.userService.create(user), HttpStatus.CREATED);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Status<?>> getUserById(@PathVariable String userId){
        try{
            User user = this.userService.getUserById(userId);
            if(user != null){
                return DefaultResponseBuilder.defaultResponse(user, HttpStatus.OK);
            }else {
                return DefaultResponseBuilder.errorResponse("User not found", null,  HttpStatus.NOT_FOUND);
            }
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by-email")
    public ResponseEntity<Status<?>> getUserByEmail(@RequestParam String email){
        try{
            User user = this.userService.getUserByEmail(email);
            if(user != null){
                return DefaultResponseBuilder.defaultResponse(user, HttpStatus.OK);
            }else {
                return DefaultResponseBuilder.errorResponse("User not found", null, HttpStatus.NOT_FOUND);
            }
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Status<?>> updateUser(@PathVariable String userId, @RequestBody User user){
        try{
            User userUpdated = this.userService.updateUser(user, userId);
            if(user != null){
                return DefaultResponseBuilder.defaultResponse(userUpdated, HttpStatus.OK);
            }else{
                return DefaultResponseBuilder.errorResponse("User not found", null, HttpStatus.NOT_FOUND);
            }
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Status<?>> deleteUser(@PathVariable String userId){
        try {
            userService.deleteUser(userId);
            return DefaultResponseBuilder.defaultResponse(new User(), HttpStatus.OK);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
