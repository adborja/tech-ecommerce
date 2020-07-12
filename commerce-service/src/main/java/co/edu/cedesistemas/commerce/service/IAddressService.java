package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Address;

import java.util.Optional;

public interface IAddressService {
    Address createAddress(Address address);

    Optional<Address> findById(String id);
}
