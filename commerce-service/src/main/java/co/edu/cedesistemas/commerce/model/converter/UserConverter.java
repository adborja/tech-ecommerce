package co.edu.cedesistemas.commerce.model.converter;

import co.edu.cedesistemas.commerce.model.User;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<User, Document> {
    @Override
    public Document convert(User user) {
        Document document = new Document();
        document.put("_id", user.getId());
        document.put("name", user.getName());
        document.put("lastName", user.getLastName());
        document.put("email", user.getEmail());
        document.remove("links");
        document.put("_class", user.getClass().getName());
        return document;
    }
}
