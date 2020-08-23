package co.edu.cedesistemas.commerce.shipping.controller;

import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import co.edu.cedesistemas.commerce.shipping.service.IShipmentService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
public class ShippingContoller {
    private final IShipmentService service;

    @PostMapping("/shipments")
    public ResponseEntity<Status<?>> createShipment(@RequestBody Shipment shipment) {
        Shipment created = service.createShipment(shipment);
        return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
    }

    @GetMapping("/shipments/{id}")
    public ResponseEntity<Status<?>> getShipmentById(@PathVariable String id) {
        Shipment found = service.getById(id);
        return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
    }

    @GetMapping("/shipments/by-track-number")
    public ResponseEntity<Status<?>> getShipmentByTrackNumber(@RequestParam String number) {
        Shipment found = service.getByTrackNumber(number);
        return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
    }

    @PatchMapping("/shipments/update/{id}")
    public ResponseEntity<Status<?>> updateDeliver(@PathVariable String id, @RequestBody Shipment shipment ) {
        try {
           if(shipment !=null && shipment.getId()!=null ){
               Shipment updated = service.updateDeliver(id , shipment);
               if (updated != null){
                   log.info("create user {}", shipment);
                   return DefaultResponseBuilder.defaultResponse(updated, HttpStatus.OK);
               }
               else return DefaultResponseBuilder.defaultResponse("shipping not found", HttpStatus.NOT_FOUND);
           }else{
               return DefaultResponseBuilder.defaultResponse("bad request", HttpStatus.BAD_REQUEST);
           }
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/shipments/cancel/{id}")
    public ResponseEntity<Status<?>> cancelShipment(@PathVariable String id,  @RequestBody Shipment shipment) {
        try {
            Shipment updated = service.cancelShipment(id, shipment);
            if (updated != null){
                return DefaultResponseBuilder.defaultResponse(updated, HttpStatus.OK);
            }
            else return DefaultResponseBuilder.defaultResponse("shipping not found", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
