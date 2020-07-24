package co.edu.cedesistemas.commerce.registration.service;

import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.commerce.registration.repository.UserRespository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    UserRespository repository;

    public User getUser(String id){
        log.info("Getting user");
        return repository.findById(id).get();
    }

    public User activateUser(String id){
        log.info("Activating user");
        User user = repository.findById(id).get();
        user.setStatus(User.Status.ACTIVE);
        return user;
    }

    public User createUser(User user){
        log.info("Creating user");
        user.setId(UUID.randomUUID().toString());
        user.setStatus(User.Status.INACTIVE);
        repository.save(user);
        return user;
    }
}
