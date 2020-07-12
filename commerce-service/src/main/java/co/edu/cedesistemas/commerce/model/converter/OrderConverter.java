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
        document.put("user", order.getUser());
        document.put("store", order.getStore());
        document.put("shippingAddress", order.getShippingAddress());
        document.put("status", order.getStatus());
        document.put("createdAt", order.getCreatedAt());
        document.put("items", order.getItems());
        document.remove("links");
        document.put("_class", order.getClass().getName());
        return document;
    }
}
