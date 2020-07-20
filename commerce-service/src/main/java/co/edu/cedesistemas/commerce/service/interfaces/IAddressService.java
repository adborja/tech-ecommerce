package co.edu.cedesistemas.commerce.service.interfaces;

import co.edu.cedesistemas.commerce.model.Address;

public interface IAddressService {

    Address getAddressById(String id);
    Address createAddress(Address address);

}
