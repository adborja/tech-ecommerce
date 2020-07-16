package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Address;

public interface IAddressService {
    Address createAddress(Address address);
    Address getById(final String id);
}
