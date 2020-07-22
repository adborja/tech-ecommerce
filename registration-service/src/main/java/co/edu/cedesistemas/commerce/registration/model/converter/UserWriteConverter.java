package co.edu.cedesistemas.commerce.registration.model.converter;
import co.edu.cedesistemas.commerce.registration.model.User;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserWriteConverter implements Converter<User, Document> {
    @Override
    public Document convert(User user) {
        Document dbo = new Document();
        dbo.put("_id", user.getId());
        dbo.put("name", user.getName());
        dbo.put("address", user.getAddress());
        dbo.put("email", user.getEmail());
        dbo.put("password", user.getPassword());
        dbo.put("birthday", user.getBirthday());
        dbo.put("createdAt", user.getCreatedAt());
        dbo.put("updatedAt", user.getUpdatedAt());
        dbo.put("status",user.getStatus().name());
        dbo.remove("links");
        dbo.put("_class", user.getClass().getName());
        return dbo;
    }
}

