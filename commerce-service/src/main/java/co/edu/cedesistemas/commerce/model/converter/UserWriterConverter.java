package co.edu.cedesistemas.commerce.model.converter;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.model.User;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;

public class UserWriterConverter implements Converter<User, Document> {
    @Override
    public Document convert(User user) {
        Document dbo = new Document();
        dbo.put("_id", user.getId());
        dbo.put("name", user.getName());
        dbo.put("lastName", user.getLastName());
        dbo.put("email", user.getEmail());
        dbo.remove("links");
        dbo.put("_class", user.getClass().getName());
        return dbo;
    }
}
