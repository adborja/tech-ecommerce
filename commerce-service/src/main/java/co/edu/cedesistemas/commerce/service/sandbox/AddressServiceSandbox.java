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
        address.setId(UUID.randomUUID().toString());
        return address;
    }

    @Override
    public Address getById(String id) {
        return Address.builder().id(id)
                .name("face name")
                .address("fake adress")
                .city("Medellín")
                .countryISOCode("055010")
                .regionISOCode("055010")
                .phoneNumber("123-123-123")
                .street1("street fake1")
                .street2("steeet fake2")
                .street3("street fake3")
                .zip("055010").build();
    }
}
