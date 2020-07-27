package co.edu.cedesistemas.commerce.model.converter;

import co.edu.cedesistemas.commerce.model.Product;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductWriteConverter implements Converter<Product, Document> {
    @Override
    public Document convert(Product product) {
        Document dbo = new Document();
        dbo.put("_id", product.getId());
        dbo.put("name", product.getName());
        dbo.put("description", product.getDescription());
        dbo.remove("links");
        dbo.put("_class", product.getClass().getName());
        return dbo;
    }
}
