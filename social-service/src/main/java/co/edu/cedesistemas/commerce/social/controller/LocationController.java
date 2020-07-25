package co.edu.cedesistemas.commerce.social.controller;

import co.edu.cedesistemas.commerce.social.model.Location;
import co.edu.cedesistemas.commerce.social.model.Product;
import co.edu.cedesistemas.commerce.social.service.LocationService;
import co.edu.cedesistemas.commerce.social.service.ProductService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class LocationController {
    private final LocationService service;

    @PostMapping("/locations")
    public ResponseEntity<Status<?>> createUser(@RequestBody Location location) {
        Location created = service.createLocation(location);
        return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
    }

}