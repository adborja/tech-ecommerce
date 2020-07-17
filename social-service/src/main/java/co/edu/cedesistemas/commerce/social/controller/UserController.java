package co.edu.cedesistemas.commerce.social.controller;

import co.edu.cedesistemas.commerce.social.model.User;
import co.edu.cedesistemas.commerce.social.service.ProductService;
import co.edu.cedesistemas.commerce.social.service.UserService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping("/users")
    public ResponseEntity<Status<?>> createUser(@RequestBody User user) {
        try{
            return DefaultResponseBuilder.defaultResponse(service.update(user), HttpStatus.CREATED);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse("generic error", ex,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}/products/{productId}/like")
    public ResponseEntity<Status<?>> like(@PathVariable String id, @PathVariable String productId) {
        try{

            return DefaultResponseBuilder.defaultResponse(service.likeProduct(id,productId), HttpStatus.CREATED);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse("generic error", ex,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}/stores/{storeId}/like")
    public ResponseEntity<Status<?>> storeLike(@PathVariable String id, @PathVariable String storeId) {
        try{
            return DefaultResponseBuilder.defaultResponse(service.likeStore(id,storeId), HttpStatus.CREATED);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse("generic error", ex,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}/stores/{storeId}/rate")
    public ResponseEntity<Status<?>> storeRate(@PathVariable String id, @PathVariable String storeId,
                                               @RequestParam float rate) {
        try{
            service.rateStore(id,storeId,rate);
            return DefaultResponseBuilder.defaultResponse("Rated", HttpStatus.CREATED);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse("generic error", ex,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public ResponseEntity<Status<?>> addFriend(@PathVariable String id, @PathVariable String friendId) {
        try{
            service.addFriend(id,friendId);
            return DefaultResponseBuilder.defaultResponse("friend added", HttpStatus.CREATED);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse("generic error", ex,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Status<?>> getUserById(@PathVariable String id) {
        try{
            return DefaultResponseBuilder.defaultResponse(service.getById(id), HttpStatus.CREATED);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse("generic error", ex,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}/stores/recommend")
    public ResponseEntity<Status<?>> recommendStores(@PathVariable String id, @RequestParam String zone,
                                                     @RequestParam String productType, @RequestParam Integer limit) {

        try{
            return DefaultResponseBuilder.defaultResponse(service.recomendations(id,zone,productType,limit), HttpStatus.CREATED);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse("generic error", ex,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}