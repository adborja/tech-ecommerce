package co.edu.cedesistemas.commerce.model.converter;

import co.edu.cedesistemas.commerce.model.Product;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;

public class ProductWriteConverter implements Converter<Product, Document> {
    @Override
    public Document convert(Product prd) {
        Document dbo = new Document();
        dbo.put("_id", prd.getId());
        dbo.put("name", prd.getName());
        dbo.put("phone", prd.getDescription());
        dbo.remove("links");
        dbo.put("_class", prd.getClass().getName());
        return dbo;
    }
}
