package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.commerce.repository.AddressRepository;
import co.edu.cedesistemas.commerce.repository.StoreRepository;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Profile("!"+SpringProfile.SANDBOX)
public class AddressService implements IAddressService {
    private final AddressRepository repository;

    public Address createAddress(final Address address) {
        address.setId(UUID.randomUUID().toString());
        return repository.save(address);
    }

    public Address getById(final String id) {
        return repository.findById(id).orElse(null);
    }

}
