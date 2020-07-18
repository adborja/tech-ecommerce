package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.repository.OrderRepository;
import co.edu.cedesistemas.commerce.repository.UserRepository;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository repository;

    public Order createOrder(final Order order) {
        //order.setId(UUID.randomUUID().toString());
        return repository.save(order);
    }

    public Order getById(final String id) {
        return repository.findById(id).orElse(null);
    }
/*
    public List<User> getByEmail(final String email) {
        return repository.findByEmailLike(email);
    }

    public void deleteUserById (final String id){
        repository.deleteById(id);
    }

    public User updateUser (final String id, final User User){
        User updatedUser = repository.findById(id).orElse(null);
        BeanUtils.copyProperties(User,updatedUser, Utils.getNullPropertyNames(User));
        return repository.save(updatedUser);
    }

 */
}
