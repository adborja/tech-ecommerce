package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;

public interface IOrderService {
    public Order createOrder(final Order order) throws Exception;
    public Order getById(final String id) throws Exception;

}
