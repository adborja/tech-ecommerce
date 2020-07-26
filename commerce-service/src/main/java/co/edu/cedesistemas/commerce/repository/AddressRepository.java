package co.edu.cedesistemas.commerce.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.edu.cedesistemas.commerce.model.Address;


public interface AddressRepository extends MongoRepository<Address, String>{
	
}
