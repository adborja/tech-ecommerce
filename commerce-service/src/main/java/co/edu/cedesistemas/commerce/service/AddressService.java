package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.repository.AddressRepository;
import co.edu.cedesistemas.commerce.service.interfaces.IAddressService;
import co.edu.cedesistemas.common.SpringProfile;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("!" + SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
public class AddressService implements IAddressService {

    private final AddressRepository repository;

    public Address getAddressById(String id){
        return repository.getById(id);
    }

    public Address createAddress(Address address){
        return repository.save(address);
    }

}
