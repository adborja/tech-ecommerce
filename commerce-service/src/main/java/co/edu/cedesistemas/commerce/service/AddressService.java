package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.repository.AddressRepository;
import co.edu.cedesistemas.common.SpringProfile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;


import java.util.Optional;


//@XSlf4j
//@AllArgsConstructor
@Profile("!" + SpringProfile.SANDBOX)
@AllArgsConstructor
@Service
public class AddressService implements IAddressService {

    private final AddressRepository repository;

    //public AddressService (AddressRepository repository){
      //  this.repository = repository;
    //}

    public Address createAddress(final Address address) {
          return repository.save(address);
    }

    public Address getById(final String id) {
        Optional<Address>  addressId = repository.findById(id);
        if (addressId.isPresent()) {
            return addressId.get();
        }
        else {return null;}

    }

    public Address updateAddress(String id, Address address)
    {
        Optional<Address> addressUpdate = repository.findById(id);
        if (addressUpdate.isPresent())
        {
            return repository.save(address);
        }
        else {
            return null;

        }

    }

}
