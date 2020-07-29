package co.edu.cedesistemas.commerce.social.controller;

import co.edu.cedesistemas.commerce.social.model.User;
import co.edu.cedesistemas.commerce.social.repository.StoreRepository;
import co.edu.cedesistemas.commerce.social.service.UserService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserService service;

    @PostMapping("/users")
    public ResponseEntity<Status<?>> createUser(@RequestBody User user) {
    	try {
    		log.info("Attempt to create user");
			User created = service.createUser(user);
			return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
		} catch (Exception ex) {
			return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @PutMapping("/users/{id}/products/{productId}/like")
    public ResponseEntity<Status<?>> like(@PathVariable String id, @PathVariable String productId) {
        try {
        	log.info("Attempt to like product {} by user {}",productId,id);
			service.likeProduct(id,productId);
		} catch (Exception e) {
			return DefaultResponseBuilder.errorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        return DefaultResponseBuilder.defaultResponse(null,HttpStatus.OK);
    }

    @PutMapping("/users/{id}/stores/{storeId}/like")
    public ResponseEntity<Status<?>> storeLike(@PathVariable String id, @PathVariable String storeId) {
    	try {
    		log.info("Attempt to like store {} by user {}",storeId,id);
			service.likeStore(id,storeId);
		} catch (Exception e) {
			return DefaultResponseBuilder.errorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        return DefaultResponseBuilder.defaultResponse(null,HttpStatus.OK);
    }

    @PutMapping("/users/{id}/stores/{storeId}/rate")
    public ResponseEntity<Status<?>> storeRate(@PathVariable String id, @PathVariable String storeId,
                                               @RequestParam float rate) {
    	try {
    		log.info("Attempt to rate with {} store {} by user {}",rate,storeId,id);
			service.rateStore(id, storeId, rate);
		} catch (Exception e) {
			return DefaultResponseBuilder.errorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        return DefaultResponseBuilder.defaultResponse(null,HttpStatus.OK);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public ResponseEntity<Status<?>> addFriend(@PathVariable String id, @PathVariable String friendId) {
    	try {
    		log.info("Attempt to add friend user {} by user {}",friendId,id);
			service.addFriend(id, friendId);
		} catch (Exception e) {
			return DefaultResponseBuilder.errorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        return DefaultResponseBuilder.defaultResponse("user added",HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Status<?>> getUserById(@PathVariable String id) {
    	try {
    		log.info("Attempt to get user {}",id);
			User user = service.getById(id);
			if(user == null)
				return DefaultResponseBuilder.defaultResponse("user not found", HttpStatus.NOT_FOUND);
			return DefaultResponseBuilder.defaultResponse(user,HttpStatus.OK);
		} catch (Exception e) {
			return DefaultResponseBuilder.errorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }

    @GetMapping("/users/{id}/stores/recommend")
    public ResponseEntity<Status<?>> recommendStores(@PathVariable String id, @RequestParam String zone,
                                                     @RequestParam String productType, @RequestParam Integer limit) {
    	try {
    		log.info("Attempt to recommend stores by user {}",id);
			List<StoreRepository.StoreOccurrence> recommendedStores = service.recommendStores(id, zone, productType, limit);
			if (recommendedStores.isEmpty())
				return DefaultResponseBuilder.defaultResponse("stores not found", HttpStatus.NOT_FOUND);
			return DefaultResponseBuilder.defaultResponse(recommendedStores,HttpStatus.OK);
		} catch (Exception e) {
			return DefaultResponseBuilder.errorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Status<?>> deleteById(@PathVariable String id) {
        service.deleteUser(id);
        return new ResponseEntity<>(Status.success(), HttpStatus.OK);
    }
}