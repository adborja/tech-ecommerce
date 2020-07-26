package co.edu.cedesistemas.commerce.registration.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.edu.cedesistemas.commerce.registration.model.User;

public interface UserRepository extends MongoRepository<User, String>{
	
	
}
