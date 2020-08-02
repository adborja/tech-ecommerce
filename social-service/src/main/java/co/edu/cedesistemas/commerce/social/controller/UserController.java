package co.edu.cedesistemas.commerce.social.controller;

import co.edu.cedesistemas.commerce.social.model.User;
import co.edu.cedesistemas.commerce.social.repository.StoreRepository;
import co.edu.cedesistemas.commerce.social.service.StoreService;
import co.edu.cedesistemas.commerce.social.service.UserService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserService service;
    private final StoreService storeService;

    @PostMapping("/users")
    public ResponseEntity<Status<?>> createUser(@RequestBody User user) {
        User user1 = service.createUser(user.getId());
        return DefaultResponseBuilder.defaultResponse(user1, HttpStatus.OK);
    }

    @PutMapping("/users/{id}/products/{productId}/like")
    public ResponseEntity<Status<?>> like(@PathVariable String id, @PathVariable String productId) {
        try {
            service.likeProduct(id, productId);
            log.info("le gusto el producto", productId);
            return DefaultResponseBuilder.defaultResponse("product like",HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return DefaultResponseBuilder.errorResponse(e.getMessage(),e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}/stores/{storeId}/like")
    public ResponseEntity<Status<?>> storeLike(@PathVariable String id, @PathVariable String storeId) {

        try {
            service.likeStore(id, storeId);
            return DefaultResponseBuilder.defaultResponse("",HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return DefaultResponseBuilder.errorResponse(e.getMessage(),e,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/users/{id}/stores/{storeId}/rate")
    public ResponseEntity<Status<?>> storeRate(@PathVariable String id, @PathVariable String storeId,
                                               @RequestParam float rate) {
        try {
            service.rateStore(id, storeId,rate);
            return DefaultResponseBuilder.defaultResponse("",HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return DefaultResponseBuilder.errorResponse(e.getMessage(),e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public ResponseEntity<Status<?>> addFriend(@PathVariable String id, @PathVariable String friendId) {
        try {
            service.addFriend(id, friendId);
            log.info(id + "es amigo de " + friendId);
            return DefaultResponseBuilder.defaultResponse(id + "es amigo de " + friendId ,HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return DefaultResponseBuilder.errorResponse(e.getMessage(),e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Status<?>> getUserById(@PathVariable String id) {
        try {
            User user = service.getById(id);
            log.info("el usuario consultado es" + id);
            return DefaultResponseBuilder.defaultResponse(user, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error("error consultando el usuario",e);
            return DefaultResponseBuilder.errorResponse("error consultando el usuario",e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}/stores/recommend")
    public ResponseEntity<Status<?>> recommendStores(@PathVariable String id, @RequestParam String zone,
                                                     @RequestParam String productType, @RequestParam Integer limit) {

        List<StoreRepository.StoreOccurrence> storeOccurrences = service.recommendStores(id, zone, productType, limit);
        return DefaultResponseBuilder.defaultResponse(storeOccurrences,HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Status<?>> deleteUser(@PathVariable String id){
        service.deleteUser(id);
        return DefaultResponseBuilder.defaultResponse("user delete" + id,HttpStatus.OK);
    }
}