package co.edu.cedesistemas.commerce.model.converter;

import co.edu.cedesistemas.commerce.model.Product;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter implements Converter<Product, Document> {

    @Override
    public Document convert(Product product) {
        Document document = new Document();
        document.put("_id", product.getId());
        document.put("name", product.getName());
        document.put("description", product.getDescription());
        document.remove("links");
        document.put("_class", product.getClass().getName());
        return document;
    }
}
