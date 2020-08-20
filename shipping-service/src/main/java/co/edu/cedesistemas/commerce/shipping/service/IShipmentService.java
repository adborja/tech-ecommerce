package co.edu.cedesistemas.commerce.shipping.service;

import co.edu.cedesistemas.commerce.shipping.model.Shipment;

public interface IShipmentService {
    Shipment createShipment(Shipment shipment);
    Shipment getById(String id);
    Shipment getByTrackNumber(String trackNumber);
    Shipment updateDelivery(String id);
    Shipment updateStatusDeliver(String id, Shipment shipment);
    Shipment cancelDelivery(String id, Shipment shipment);
}
