package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.Store;

import java.util.List;
import java.util.Optional;

public interface IOrderService {

    Order createOrder(Order order);

    Optional<Order> getOrder(String id);

}
