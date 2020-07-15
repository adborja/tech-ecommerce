package co.edu.cedesistemas.commerce.sandbox;

import java.util.UUID;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.service.IAddressService;

public class AddressServiceSandbox implements IAddressService{

	@Override
	public Address createAddress(Address address) {
		address.setId(UUID.randomUUID().toString());
		return address;
	}

	@Override
	public Address getById(String id) {
		Address address = new Address();
		address.setCity("Ciudad");
		address.setDescription("mi descripcion");
		address.setName("mi direccion");
		return address;
	}

}
