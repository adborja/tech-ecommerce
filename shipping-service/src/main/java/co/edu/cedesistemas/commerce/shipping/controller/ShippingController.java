package co.edu.cedesistemas.commerce.shipping.controller;

import co.edu.cedesistemas.commerce.shipping.model.CancelReason;
import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import co.edu.cedesistemas.commerce.shipping.service.IShipmentService;
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
    
    @PutMapping("/shipment/{id}/deliver")
    public ResponseEntity<Status<?>> deliver(@PathVariable String id){
    	Shipment found = service.deliver(id);
    	if(found == null)
    		return DefaultResponseBuilder.defaultResponse("shipment not found", HttpStatus.NOT_FOUND);
    	
    	
    	return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
    }
    
    @PostMapping("/shipment/{id}/cancel")
    public ResponseEntity<Status<?>> cancel(@PathVariable String id, @RequestBody CancelReason reason){
    	Shipment found = service.cancel(id, reason);
    	if(found == null)
    		return DefaultResponseBuilder.defaultResponse("shipment not found", HttpStatus.NOT_FOUND);
    
    	return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
    }
    
    @PutMapping("/shipment/{id}/status/{status}")
    public ResponseEntity<Status<?>> changeStatus(@PathVariable String id, @PathVariable String status){
    	Shipment found = service.changeStatus(id, status);
    	if(found == null)
    		return DefaultResponseBuilder.defaultResponse("shipment not found", HttpStatus.NOT_FOUND);
    	
    	return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
    }

    @GetMapping("/shipments/by-track-number")
    public ResponseEntity<Status<?>> getShipmentByTrackNumber(@RequestParam String number) {
        Shipment found = service.getByTrackNumber(number);
        return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
    }

    @GetMapping("/shipments/deliver")
    public ResponseEntity<Status<?>> deliver(@PathVariable String id) {
        Shipment found = service.deliver(id);
        return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
    }

    @GetMapping("/shipments/changeStatus")
    public ResponseEntity<Status<?>> changeStatus(@PathVariable String id, @PathVariable Shipment.Status status) {
        Shipment found = service.changeStatus(id,status);
        return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
    }


    @GetMapping("/shipments/cancelDeliver")
    public ResponseEntity<Status<?>> cancelDeliver(@PathVariable String id,@PathVariable Shipment.Motivo motivo, @PathVariable String descMotivo ) {
        Shipment found = service.cancelDeliver(id,motivo,descMotivo);
        return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
    }


}
