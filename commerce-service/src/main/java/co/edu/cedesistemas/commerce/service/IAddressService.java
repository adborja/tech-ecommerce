package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.model.Store;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface IAddressService {

    Address createAddress(Address address);

    Optional<Address> getAddress(String id);

}
