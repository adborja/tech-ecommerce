package co.edu.cedesistemas.commerce.registration.service;

import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.commerce.registration.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository repository;

    public User createUser(User user) {
        log.info("creating user {}", user.getName());
    	user.setStatus(User.Status.INACTIVE);
        return repository.save(user);
    }

    public User getById(String id) {
        log.info("getting user by id: {}", id);
        return repository.findById(id).orElse(null);
    }

    public User activateUser(String id) {
        log.info("activate user with id: {}", id);
        User userActive = getById(id);
        userActive.setStatus(User.Status.ACTIVE);
        return repository.save(userActive);

    }
}
