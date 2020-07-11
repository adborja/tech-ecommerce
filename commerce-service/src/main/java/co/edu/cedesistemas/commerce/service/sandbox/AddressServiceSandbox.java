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
    public Address create(Address address) {
        address.setId(UUID.randomUUID().toString());
        address.setCity("Medellin");
        address.setCountryISOCode("CO");
        address.setRegionISOCode("ANT");
        address.setDescription("home");
        address.setName("home");
        address.setPhoneNumber("0344445878");
        address.setStreet1("cra 80 # 33 - 50");
        address.setStreet2("apto. 505");
        address.setZip("50032");
        return address;
    }

    @Override
    public Address getById(String id) {
        return Address.builder()
                    .id(UUID.randomUUID().toString())
                    .city("Medellin")
                    .countryISOCode("CO")
                    .regionISOCode("ANT")
                    .description("home")
                    .name("home")
                    .phoneNumber("0344445878")
                    .street1("cra 80 # 33 - 50")
                    .street2("apto. 505")
                    .zip("50032")
                .build();
    }
}
