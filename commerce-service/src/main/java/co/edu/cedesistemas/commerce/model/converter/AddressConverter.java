package co.edu.cedesistemas.commerce.model.converter;

import co.edu.cedesistemas.commerce.model.Address;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AddressConverter implements Converter<Address, Document> {

    @Override
    public Document convert(Address address) {
        Document document = new Document();
        document.put("_id", address.getId());
        document.put("name", address.getName());
        document.put("description", address.getDescription());
        document.put("city", address.getCity());
        document.put("countryISOCode", address.getCountryISOCode());
        document.put("regionISOCode", address.getRegionISOCode());
        document.put("phoneNumber", address.getPhoneNumber());
        document.put("street1", address.getStreet1());
        document.put("street2", address.getStreet2());
        document.put("street3", address.getStreet3());
        document.put("zip", address.getZip());
        document.remove("links");
        document.put("_class", address.getClass().getName());
        return document;
    }
}
