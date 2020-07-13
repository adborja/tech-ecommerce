package co.edu.cedesistemas.commerce.model.converter;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.Store;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderWriteConverter implements Converter<Order, Document> {
    @Override
    public Document convert(Order order) {
        Document document = new Document();
        document.put("_id", order.getId());
        document.put("userId", order.getUser().getId());
        document.put("storeId", order.getStore().getId());
        document.put("shippingAddressId", order.getShippingAddress().getId());
        document.put("status", order.getStatus().name());
        document.put("createdAt", order.getCreatedAt());
        document.put("items", order.getItems());
        document.remove("links");
        document.put("_class", order.getClass().getName());
        return document;
    }
}
