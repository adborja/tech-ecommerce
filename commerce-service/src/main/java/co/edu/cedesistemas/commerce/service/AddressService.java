package co.edu.cedesistemas.commerce.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.repository.AddressRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AddressService {

	private final AddressRepository repository;
	
	public Address createAddress(final Address address) {
		return repository.save(address);
	}
	
	public Address getById(final String id) {
		Optional<Address> address = repository.findById(id);
		return address.isPresent() ? address.get() : null;
	}
	
	
	
}
