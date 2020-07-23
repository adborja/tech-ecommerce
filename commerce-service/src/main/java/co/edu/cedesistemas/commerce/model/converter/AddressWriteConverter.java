package co.edu.cedesistemas.commerce.model.converter;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.model.Store;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AddressWriteConverter implements Converter<Address, Document> {
    @Override
    public Document convert(Address address) {
        Document dbo = new Document();
        dbo.put("_id", address.getId());
        dbo.put("name", address.getName());
        dbo.put("phone", address.getDescription());
        dbo.put("address", address.getCity());
        dbo.put("type", address.getCountryISOCode());
        dbo.put("createdAt", address.getRegionISOCode());
        dbo.put("updatedAt", address.getPhoneNumber());
        dbo.put("updatedAt", address.getStreet1());
        dbo.put("updatedAt", address.getStreet2());
        dbo.put("updatedAt", address.getStreet3());
        dbo.put("updatedAt", address.getZip());
        dbo.remove("links");
        dbo.put("_class", address.getClass().getName());
        return dbo;

    }
}
