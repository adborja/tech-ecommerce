package co.edu.cedesistemas.commerce.service.sandbox;


import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.service.interfaces.IAddressService;
import co.edu.cedesistemas.common.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Profile(SpringProfile.SANDBOX)
@Service
public class AddressServiceSandbox implements IAddressService {

    @Override
    public Address getAddressById(String id) {
        Address address = new Address();
        address.setCity("Medellin");
        address.setCountryISOCode("FakeISO1234");
        address.setDescription("Fake Street");
        address.setId(id);
        address.setName("FakeStreet123");
        address.setPhoneNumber("13246456");
        address.setRegionISOCode("123456");
        address.setZip("00123100");
        address.setStreet1("FakeStreet - 123154");
        address.setStreet2("FakeStreet123 qqewq");
        address.setStreet3("FakeStreet123 123123");
        return address;
    }

    @Override
    public Address createAddress(Address address) {
        address.setCity("Bogota");
        address.setCountryISOCode("FakeISO8778789");
        address.setDescription("Fake Street 4qe");
        address.setId(UUID.randomUUID().toString());
        address.setName("FakeStreet987");
        address.setPhoneNumber("985436");
        address.setRegionISOCode("98433");
        address.setZip("0050250");
        address.setStreet1("Fake Street - 0812903");
        address.setStreet2("Fake Street - 8912839");
        address.setStreet3("Fake Street - 9123789123");
        return address;
    }
}