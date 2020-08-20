package co.edu.cedesistemas.commerce.shipping.service;

import co.edu.cedesistemas.commerce.shipping.model.Cancel;
import co.edu.cedesistemas.commerce.shipping.model.Shipment;

public interface IShipmentService {
    Shipment createShipment(Shipment shipment);
    Shipment getById(String id);
    Shipment getByTrackNumber(String trackNumber);
    Shipment updateStatus(String id);
    Shipment cancelShipment(String id, Cancel cancel);

}
