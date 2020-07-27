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
    public Address getById(String id) {
        return Address.builder()
                .id(id)
                .city("Bogotá")
                .countryISOCode("CO")
                .regionISOCode("CUN")
                .description("pier30")
                .name("work")
                .phoneNumber("03457345878")
                .street1("calle 34 # 423 - 45")
                .street2("apto. 4")
                .zip("50012")
                .build();
    }
}
