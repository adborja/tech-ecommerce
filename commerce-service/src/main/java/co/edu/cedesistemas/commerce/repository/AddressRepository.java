package co.edu.cedesistemas.commerce.repository;

import co.edu.cedesistemas.commerce.model.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddressRepository extends MongoRepository<Address, String> {

}
