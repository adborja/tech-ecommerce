package co.edu.cedesistemas.commerce.model.converter;

import co.edu.cedesistemas.commerce.model.Order;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter implements Converter<Order, Document> {
    @Override
    public Document convert(Order order) {
        Document document = new Document();
        document.put("_id", order.getId());
        document.put("userId", order.getUserId());
        document.put("storeId", order.getStoreId());
        document.put("shippingAddressId", order.getShippingAddressId());
        document.put("status", order.getStatus().name());
        document.put("createdAt", order.getCreatedAt());
        document.put("items", order.getItems());
        document.remove("links");
        document.put("_class", order.getClass().getName());
        return document;
    }
}
