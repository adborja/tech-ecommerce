package co.edu.cedesistemas.commerce.shipping.controller;

import co.edu.cedesistemas.commerce.shipping.model.Cancel;
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

    @PatchMapping("/shipments/{id}/deliver")
    public ResponseEntity<Status<?>> deliverShipmentDelivery(@PathVariable String id)
    {
        Shipment updated = service.updateStatus(id);
        return DefaultResponseBuilder.defaultResponse(updated, HttpStatus.OK);
    }

    @PatchMapping("/shipments/{id}/cancel")
    public ResponseEntity<Status<?>> cancelShipmentDelivery(@PathVariable String id, @RequestBody Cancel cancel)
    {
        Shipment updated = service.cancelShipment(id,cancel);
        return DefaultResponseBuilder.defaultResponse(updated, HttpStatus.OK);
    }


    @PatchMapping("/shipments/{id}/update")
    public ResponseEntity<Status<?>> updateShipmentDelivery(@PathVariable String id, @RequestBody Cancel cancel)
    {
        Shipment updated = service.updateStatus(id);
        return DefaultResponseBuilder.defaultResponse(updated, HttpStatus.OK);
    }
}
