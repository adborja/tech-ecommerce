package co.edu.cedesistemas.commerce.shipping.service;

import co.edu.cedesistemas.commerce.shipping.model.Shipment;

public interface IShipmentService {
    Shipment createShipment(Shipment shipment);
    Shipment getById(String id);
    Shipment getByTrackNumber(String trackNumber);
    Shipment deliver(String id);
    Shipment changeStatus(String id, Shipment.Status status);
    Shipment cancelDeliver(String id, Shipment.Motivo motivo);
}
