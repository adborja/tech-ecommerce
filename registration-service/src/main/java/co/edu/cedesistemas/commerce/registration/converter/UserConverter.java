package co.edu.cedesistemas.commerce.registration.converter;

import co.edu.cedesistemas.commerce.registration.model.User;
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
        document.put("address", user.getAddress());
        document.put("email", user.getEmail());
        document.put("password", user.getPassword());
        document.put("birthday", user.getBirthday());
        document.put("createdAt", user.getCreatedAt());
        document.put("updatedAt", user.getUpdatedAt());
        document.put("status", user.getStatus().name());
        document.remove("links");
        document.put("_class", user.getClass().getName());
        return document;
    }
}
