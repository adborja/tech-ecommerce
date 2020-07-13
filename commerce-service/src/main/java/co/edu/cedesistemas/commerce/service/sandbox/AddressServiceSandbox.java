package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.service.IAddressService;
import co.edu.cedesistemas.common.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Profile(SpringProfile.SANDBOX)
@Service
public class AddressServiceSandbox implements IAddressService {
    @Override
    public Address createAddress(Address address) {
        System.out.println("probando el sandbox");
        address.setId(UUID.randomUUID().toString());
        return address;
    }

    @Override
    public Optional<Address> findById(String id) {
        Address address = new Address();
        address.setId(id);
        address.setCity("Medellin");
        address.setCountryISOCode("03930393");
        address.setDescription("Prueba");
        address.setName("Calle");
        address.setPhoneNumber("093939393");
        address.setRegionISOCode("093939393");
        address.setStreet1("11111");
        address.setStreet2("2222222");
        address.setStreet3("3333333");
        address.setZip("lakdkdkdk");
        return Optional.of(address);
    }
}
