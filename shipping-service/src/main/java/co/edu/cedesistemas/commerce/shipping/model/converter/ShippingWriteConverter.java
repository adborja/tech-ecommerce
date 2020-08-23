package co.edu.cedesistemas.commerce.shipping.model.converter;

import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;

public class ShippingWriteConverter implements Converter<Shipment, Document> {

    @Override
    public Document convert(Shipment shipment) {
        Document dbo = new Document();
        dbo.put("_id",shipment.getId() );
        dbo.put("order",shipment.getOrder());
        dbo.put("trackNumber", shipment.getTrackNumber());
        dbo.put("status", shipment.getStatus());
        dbo.put("shipmentCancelled", shipment.getShipmentCancelled());
        dbo.put("createdAt", shipment.getCreatedAt());

        return dbo;
    }
}
