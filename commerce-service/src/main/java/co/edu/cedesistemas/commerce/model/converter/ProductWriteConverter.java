package co.edu.cedesistemas.commerce.model.converter;

import co.edu.cedesistemas.commerce.model.Product;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductWriteConverter implements Converter<Product, Document> {
    @Override
    public Document convert(Product source) {
        Document dbo = new Document();
        dbo.put("_id", source.getId());
        dbo.put("name", source.getName());
        dbo.put("description", source.getDescription());
        dbo.remove("links");
        dbo.put("_class", source.getClass().getName());
        return dbo;
    }
}
