package co.edu.cedesistemas.commerce.shipping.service;

import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import co.edu.cedesistemas.commerce.shipping.model.ShipmentCancelReason;

public interface IShipmentService {
    Shipment createShipment(Shipment shipment);
    Shipment getById(String id);
    Shipment getByTrackNumber(String trackNumber);
    Shipment deliverShipment(String id);
    Shipment cancelShipment(String id, ShipmentCancelReason reason);
    Shipment updateShipmentStatus(String id, Shipment.Status status);
}
