package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.repository.AddressRepository;
import co.edu.cedesistemas.common.SpringProfile;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("!" + SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
public class AddressService implements IAddressService{
    private final AddressRepository addressRepository;

    @Override
    public Address create(final Address address){
        return addressRepository.save(address);
    }

    @Override
    public Address getById(final String id){

        return addressRepository.findById(id)
                .orElse(null);
    }
}
