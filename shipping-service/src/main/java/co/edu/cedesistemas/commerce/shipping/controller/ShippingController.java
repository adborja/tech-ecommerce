package co.edu.cedesistemas.commerce.shipping.controller;

import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import co.edu.cedesistemas.commerce.shipping.service.IShipmentService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.ShipmentCancellationReason;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
        Shipment created = service.createShipment(shipment);
        return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
    }

    @PatchMapping("/shipments/{id}/deliver")
    public ResponseEntity<Status<?>> deliverShipment(@PathVariable String id) {
        log.info("Delivering shipment...");
        Shipment delivered = service.deliverShipment(id);
        return DefaultResponseBuilder.defaultResponse(delivered, HttpStatus.CREATED);
    }

    @PatchMapping("/shipments/{id}/cancel")
    public ResponseEntity<Status<?>> cancelShipment(@PathVariable String id,
                                                    @RequestBody ShipmentCancellationReason cancelledReason) {
        log.info("Cancelling shipment...");
        Shipment delivered = service.cancelShipment(id, cancelledReason);
        return DefaultResponseBuilder.defaultResponse(delivered, HttpStatus.CREATED);
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
}
