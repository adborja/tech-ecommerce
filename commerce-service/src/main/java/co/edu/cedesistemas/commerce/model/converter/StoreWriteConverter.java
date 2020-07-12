package co.edu.cedesistemas.commerce.model.converter;

import co.edu.cedesistemas.commerce.model.Store;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StoreWriteConverter implements Converter<Store, Document> {
    @Override
    public Document convert(Store store) {
        Document dbo = new Document();
        dbo.put("_id", store.getId());
        dbo.put("name", store.getName());
        dbo.put("phone", store.getPhone());
        dbo.put("address", store.getAddress());
        dbo.put("type", store.getType().name());
        dbo.put("createdAt", store.getCreatedAt());
        dbo.put("updatedAt", store.getUpdatedAt());
        dbo.remove("links");
        dbo.put("_class", store.getClass().getName());
        return dbo;
    }
}
