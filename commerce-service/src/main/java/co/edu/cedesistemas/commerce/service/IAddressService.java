package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Address;

public interface IAddressService {

    public Address createAddress(Address address);
    public Address getAddressById(String id);
}
