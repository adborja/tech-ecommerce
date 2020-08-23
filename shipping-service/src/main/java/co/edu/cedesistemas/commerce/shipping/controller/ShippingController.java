package co.edu.cedesistemas.commerce.shipping.controller;

import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import co.edu.cedesistemas.commerce.shipping.model.ShipmentCancelReason;
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
public class ShippingController {
    private final IShipmentService service;

    @PostMapping("/shipments")
    public ResponseEntity<Status<?>> createShipment(@RequestBody Shipment shipment) {
        log.info("Creating shipment");
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

    @PatchMapping("/shipments/{id}/deliver")
    public ResponseEntity<Status<?>> deliverShipment(@PathVariable String id){
        try{
            Shipment shipmetnUpdated = service.deliverShipment(id);
            if(shipmetnUpdated != null){
                log.info("Shipment delivered");
                return DefaultResponseBuilder.defaultResponse(shipmetnUpdated, HttpStatus.OK);
            }else {
                return DefaultResponseBuilder.defaultResponse("Shipment Not Found", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return DefaultResponseBuilder.errorResponse(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/shipments/{id}/cancel")
    public ResponseEntity<Status<?>> cancelShipment(@PathVariable String id, @RequestBody ShipmentCancelReason reason){
        try{
            Shipment shipmentCanceled = service.cancelShipment(id, reason);
            if(shipmentCanceled != null){
                return DefaultResponseBuilder.defaultResponse(shipmentCanceled, HttpStatus.OK);
            }else {
                return DefaultResponseBuilder.defaultResponse("Shipment Not Found", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return DefaultResponseBuilder.errorResponse(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/shipments/{id}")
    public ResponseEntity<Status<?>> updateShipmentStatus(@PathVariable String id, @RequestBody Shipment.Status status){
        try{
            Shipment shipmetnUpdated = service.updateShipmentStatus(id, status);
            if(shipmetnUpdated != null){
                return DefaultResponseBuilder.defaultResponse(shipmetnUpdated, HttpStatus.OK);
            }else {
                return DefaultResponseBuilder.defaultResponse("Shipment Not Found", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return DefaultResponseBuilder.errorResponse(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
