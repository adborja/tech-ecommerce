package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AddressService implements IAddressService{
    private final AddressRepository repository;

    public Address createAddress(final Address address){
        address.setId(UUID.randomUUID().toString());
        return repository.save(address);
    }

    public Address getById(final String id){
        return repository.findById(id).orElse(null);
    }
}
