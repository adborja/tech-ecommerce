package co.edu.cedesistemas.commerce.shipping.controller;

import co.edu.cedesistemas.commerce.shipping.model.CanceledDelivery;
import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import co.edu.cedesistemas.commerce.shipping.service.IShipmentService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ShippingController {
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

    @PutMapping("/shipments/update-state")
    public ResponseEntity<Status<?>> updateShippingState(@PathVariable String id){
        Shipment found = service.update(id);
        return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
    }

    @PatchMapping("/shipments/{id}/cancel")
    public ResponseEntity<Status<?>> cancelShipmentDelivery(@PathVariable String id, @RequestBody CanceledDelivery canceledDelivery)
    {
        Shipment updated = service.cancelShipment(id,canceledDelivery);
        return DefaultResponseBuilder.defaultResponse(updated, HttpStatus.OK);
    }
}
