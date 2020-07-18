package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.repository.AddressRepository;
import co.edu.cedesistemas.commerce.service.IAddressService;
import co.edu.cedesistemas.common.SpringProfile;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@Profile(SpringProfile.SANDBOX)
public class AddressServiceSandbox implements IAddressService {
    private final AddressRepository repository;

    public Address createAddress(final Address address) {
        address.setId(UUID.randomUUID().toString());
        address.setCity("medellin");
        return address;
    }

    public Address getById(final String id) {
        Address address = new Address();
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


}
