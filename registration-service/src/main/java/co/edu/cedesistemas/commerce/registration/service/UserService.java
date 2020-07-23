package co.edu.cedesistemas.commerce.registration.service;

import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.commerce.registration.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User createUser(final User user) {
        user.setId(UUID.randomUUID().toString());
        user.setStatus(User.Status.INACTIVE);
        return repository.save(user);
    }

    public User getById(final String id) {
        return repository.findById(id).get();
    }

    public User updateUser(String id) {
        User user = getById(id);
        user.setStatus(User.Status.ACTIVE);
        return repository.save(user);
    }
}
