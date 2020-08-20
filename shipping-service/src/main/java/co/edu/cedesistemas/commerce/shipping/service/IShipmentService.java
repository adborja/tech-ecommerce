package co.edu.cedesistemas.commerce.shipping.service;

import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import co.edu.cedesistemas.common.model.ShipmentStatus;

public interface IShipmentService {
    Shipment createShipment(Shipment shipment);
    Shipment getById(String id);
    Shipment getByTrackNumber(String trackNumber);
    Shipment deliver(String id);
    Shipment cancel(Shipment.Cancel shipment);
    Shipment status(String id, ShipmentStatus status);

}
