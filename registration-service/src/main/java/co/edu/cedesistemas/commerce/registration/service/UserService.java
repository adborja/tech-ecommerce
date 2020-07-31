package co.edu.cedesistemas.commerce.registration.service;

import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.commerce.registration.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User createUser(User user) {
        user.setStatus(User.Status.INACTIVE);
        return repository.save(user);
    }

    public User getById(String id) {
        return repository.findById(id).orElse(null);
    }

    public User activateUser(String id) {

        User userActive = getById(id);
        userActive.setStatus(User.Status.ACTIVE);
        return repository.save(userActive);

    }
}