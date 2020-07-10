package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository repository;

    public Address createAddress(Address address) {

        return repository.save(address);
    }

    public Optional<Address> getById(String id) {
        return repository.findById(id);
    }
}
