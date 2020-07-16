package co.edu.cedesistemas.commerce.model.converter;

import co.edu.cedesistemas.commerce.model.User;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;

public class UserWriteConverter implements Converter<User, Document> {
    @Override
    public Document convert(User source) {
        Document dbo = new Document();
        dbo.put("_id", source.getId());
        dbo.put("name", source.getName());
        dbo.put("lastName", source.getLastName());
        dbo.put("email", source.getEmail());
        return dbo;
    }
}
