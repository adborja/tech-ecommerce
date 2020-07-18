package co.edu.cedesistemas.commerce.model.converter;

import co.edu.cedesistemas.commerce.model.Address;
import org.bson.Document;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AddressWriteConverter implements Converter<Address,Document> {
    @Override
    public Document convert(Address address) {
        Document dbo = new Document();
        dbo.put("_id", address.getId());
        dbo.put("name", address.getName());
        dbo.put("city", address.getCity());
        dbo.put("countryISOCode", address.getCountryISOCode());
        dbo.put("description", address.getDescription());
        dbo.put("phoneNumber", address.getPhoneNumber());
        dbo.put("regionISOCode", address.getRegionISOCode());
        dbo.put("street1", address.getStreet1());
        dbo.put("street2", address.getStreet2());
        dbo.put("street3", address.getStreet3());
        dbo.put("zip", address.getZip());
        dbo.remove("links");
        dbo.put("_class", address.getClass().getName());
        return dbo;
    }
}
