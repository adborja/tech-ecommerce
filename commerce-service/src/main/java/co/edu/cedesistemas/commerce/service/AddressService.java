package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.repository.AddressRepository;
import co.edu.cedesistemas.common.SpringProfile;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Profile("!" + SpringProfile.SANDBOX)
public class AddressService implements IAddressService {
    private final AddressRepository repository;

    @Override
    public Address createAddress(final Address address) {
        return repository.save(address);
    }

    @Override
    public Address getById(final String id) {
        return repository.findById(id).get();
    }

}
