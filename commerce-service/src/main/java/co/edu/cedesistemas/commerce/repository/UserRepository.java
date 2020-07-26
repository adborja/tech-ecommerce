package co.edu.cedesistemas.commerce.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.edu.cedesistemas.commerce.model.User;

public interface UserRepository extends MongoRepository<User,String>{
	
	List<User> findByEmail(String email);

}
