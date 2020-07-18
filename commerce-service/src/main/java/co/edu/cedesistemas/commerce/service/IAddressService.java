package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.model.Store;

import java.util.List;

public interface IAddressService {
    public Address createAddress(final Address address);
    public Address getById(final String id);
}
