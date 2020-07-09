package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository repository;

    public Address getAddressById(String id){
        return repository.getById(id);
    }

    public Address createAddress(Address address){
        return repository.save(address);
    }

}
