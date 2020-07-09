package co.edu.cedesistemas.commerce.repository;

import co.edu.cedesistemas.commerce.model.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends MongoRepository<Address, String> {
}
