package co.edu.cedesistemas.commerce.registration.model.converter;

import co.edu.cedesistemas.commerce.registration.model.User;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserWriteConverter implements Converter<User, Document> {

    @Override
    public Document convert(User source) {
        Document dbo = new Document();
        dbo.put("_id", source.getId());
        dbo.put("name", source.getName());
        dbo.put("lastName", source.getLastName());
        dbo.put("address", source.getAddress());
        dbo.put("email", source.getEmail());
        dbo.put("password", source.getPassword());
        dbo.put("birthday", source.getBirthday());
        dbo.put("createdAt", source.getCreatedAt());
        dbo.put("updatedAt", source.getUpdatedAt());
        dbo.put("status", source.getStatus().name());
        dbo.remove("links");
        dbo.put("_class", source.getClass().getName());
        return dbo;
    }
}
