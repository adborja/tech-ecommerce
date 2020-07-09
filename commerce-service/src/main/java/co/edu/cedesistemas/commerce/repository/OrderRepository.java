package co.edu.cedesistemas.commerce.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.edu.cedesistemas.commerce.model.Order;

public interface OrderRepository extends MongoRepository<Order, String>{

}
