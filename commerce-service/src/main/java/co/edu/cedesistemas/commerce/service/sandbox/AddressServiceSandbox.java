package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.service.IAddressService;
import co.edu.cedesistemas.common.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

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
    public Address getAddressById(String id) {
        return Address.builder()
                .city("Ciudad Falsa")
                .id(id)
                .name("Nombre Falso")
                .description("Description Falso")
                .countryISOCode("ISO Falso")
                .phoneNumber("2345678765")
                .regionISOCode("ISO Region Falso")
                .street1("Cra Falsa").build();
    }
}
