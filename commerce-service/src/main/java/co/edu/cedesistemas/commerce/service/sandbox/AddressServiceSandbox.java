package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.service.IAdressService;
import co.edu.cedesistemas.common.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Profile(SpringProfile.SANDBOX)
@Service
public class AddressServiceSandbox implements IAdressService {
    @Override
    public Address createAddress(Address address) {
        address.setId(UUID.randomUUID().toString());
        return address;
    }

    @Override
    public Address getAddressById(String id) {
        return Address.builder()
                .city("fake city")
                .id(id)
                .name("fake nombre")
                .countryISOCode("fake iso ")
                .description("fake description")
                .phoneNumber("123456789")
                .regionISOCode("fake iso region")
                .street1("kafe street").build();
    }
}
