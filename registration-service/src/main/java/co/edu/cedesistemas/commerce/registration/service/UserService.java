package co.edu.cedesistemas.commerce.registration.service;

import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.commerce.registration.repository.UserRespository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    UserRespository repository;

    public User getUser(String id){
        return repository.findById(id).get();
    }

    public User activateUser(String id){
        User user = repository.findById(id).get();
        user.setStatus(User.Status.ACTIVE);
        return user;
    }

    public User createUser(User user){
        user.setId(UUID.randomUUID().toString());
        user.setStatus(User.Status.INACTIVE);
        repository.save(user);
        return user;
    }
}
