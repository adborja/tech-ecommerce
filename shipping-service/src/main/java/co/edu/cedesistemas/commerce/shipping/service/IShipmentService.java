package co.edu.cedesistemas.commerce.shipping.service;

import co.edu.cedesistemas.commerce.shipping.model.CanceledDelivery;
import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import co.edu.cedesistemas.common.model.ShipmentStatus;

public interface IShipmentService {
    Shipment createShipment(Shipment shipment);
    Shipment getById(String id);
    Shipment getByTrackNumber(String trackNumber);
    Shipment update(String id, ShipmentStatus status);
    Shipment deliver(String id);
    Shipment cancelShipment(String id, CanceledDelivery canceledDelivery);
}
