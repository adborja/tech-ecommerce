package co.edu.cedesistemas.commerce.service;


import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.repository.AddressRepository;
import co.edu.cedesistemas.common.SpringProfile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Profile("!"+ SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
@Slf4j
public class AddressService implements IAddressService {
    private final AddressRepository repository;

    public Address createAddress(final Address address) {
        log.info("creating Address {}", address.getName());
        address.setId(UUID.randomUUID().toString());
        return repository.save(address);
    }

    public Address getById(final String id) {
        log.info("Retrieving Address by Id ", id);
        return repository.findById(id).get();
    }
}
