package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AddressService {

    private AddressRepository addressRepository;

    public Address createAddress(Address address) {
        return this.addressRepository.save(address);
    }

    public Optional<Address> findById(String id) {
        return this.addressRepository.findById(id);
    }
}
