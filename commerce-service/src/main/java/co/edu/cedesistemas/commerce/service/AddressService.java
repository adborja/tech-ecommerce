package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.repository.AddressRepository;
import co.edu.cedesistemas.common.SpringProfile;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = "commerce_service_address", key = "#id")
    public Address getAddressById(String id) {

        try {
           // log.info("simulating long time operation");
            Thread.sleep(5000L);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        return repository.findById(id).get();
    }
}