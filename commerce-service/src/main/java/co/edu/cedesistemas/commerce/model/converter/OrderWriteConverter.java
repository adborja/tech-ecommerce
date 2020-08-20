package co.edu.cedesistemas.commerce.model.converter;

import co.edu.cedesistemas.commerce.model.Order;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderWriteConverter implements Converter<Order, Document> {
    @Override
    public Document convert(Order source) {
        Document dbo = new Document();
        dbo.put("_id", source.getId());
        dbo.put("createdAt", source.getCreatedAt());
        dbo.put("items", source.getItems());
        dbo.put("shippingAddressId", source.getShippingAddressId());
        dbo.put("status", source.getStatus());
        dbo.put("storeId", source.getStoreId());
        dbo.put("userId", source.getUserId());
        dbo.remove("links");
        dbo.put("_class", source.getClass().getName());
        return dbo;
    }
}
