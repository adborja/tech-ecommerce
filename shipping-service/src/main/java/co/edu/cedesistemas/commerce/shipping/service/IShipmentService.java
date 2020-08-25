package co.edu.cedesistemas.commerce.shipping.service;

import co.edu.cedesistemas.commerce.shipping.model.CancelReason;
import co.edu.cedesistemas.commerce.shipping.model.Shipment;

public interface IShipmentService {
    Shipment createShipment(Shipment shipment);
    Shipment getById(String id);
    Shipment getByTrackNumber(String trackNumber);
    Shipment deliver(String id);
    Shipment cancel(String id, CancelReason reason);
    Shipment changeStatus(String id, String status);
}
