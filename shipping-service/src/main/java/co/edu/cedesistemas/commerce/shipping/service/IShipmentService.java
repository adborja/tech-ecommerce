package co.edu.cedesistemas.commerce.shipping.service;

import co.edu.cedesistemas.commerce.shipping.model.Shipment;

public interface IShipmentService {
    Shipment deliver(String id);
    Shipment send(String id,String trackNumber);
    Shipment cancel(String id);
    Shipment createShipment(Shipment shipment);
    Shipment getById(String id);
    Shipment getByTrackNumber(String trackNumber);
}
