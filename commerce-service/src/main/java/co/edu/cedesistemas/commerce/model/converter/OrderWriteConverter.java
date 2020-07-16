package co.edu.cedesistemas.commerce.model.converter;

import co.edu.cedesistemas.commerce.model.Order;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;


public class OrderWriteConverter implements Converter<Order, Document> {
    @Override
    public Document convert(Order order) {
        Document dbo = new Document();
        dbo.put("_id", order.getId());
        dbo.put("userId", order.getUserId());
        dbo.put("storeId", order.getStoreId());
        dbo.put("shippingAddressId", order.getShippingAddressId());
        dbo.put("status", order.getStatus());
        dbo.put("createdAt", order.getCreatedAt());
        dbo.put("items", order.getItems());
        return dbo;
    }
}
