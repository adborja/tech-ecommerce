package co.edu.cedesistemas.commerce.repository;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.Product;

import co.edu.cedesistemas.commerce.model.OrderItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Product, String> {


    List<OrderItem> GetOrderItems(String name);
}
