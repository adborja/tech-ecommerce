package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.repository.AddressRepository;
import co.edu.cedesistemas.common.SpringProfile;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Service
@Profile("!"+ SpringProfile.SANDBOX)

public class AddressService implements IAdressService{
    private AddressRepository repository;

    public Address createAddress(Address address){
        address.setId(UUID.randomUUID().toString());
        return repository.save(address);
    }

    public Address getAddressById(String id) {
        return repository.findById(id).get();
    }
}