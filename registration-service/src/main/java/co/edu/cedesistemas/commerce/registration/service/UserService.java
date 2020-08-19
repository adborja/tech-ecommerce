package co.edu.cedesistemas.commerce.registration.service;

import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.commerce.registration.repository.UserRepository;
import co.edu.cedesistemas.common.event.RegistrationEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final EventPublisherService publisherService;

    public User createUser(final User user) {
        user.setStatus(User.Status.ACTIVE);
        User created = repository.save(user);
        publisherService.publishRegistrationEvent(created, RegistrationEvent.Status.USER_CREATED);
        return created;
    }

    public User activateUser(String id) {
        User userActive = getById(id);
        userActive.setStatus(User.Status.ACTIVE);

        return repository.save(userActive);

    }

    public void deleteUser(String id){

        User found = getById(id);
        if (found != null) {
            repository.deleteById(id);
            publisherService.publishRegistrationEvent(found, RegistrationEvent.Status.USER_DELETED);
        }
    }

    public User getById(final String id) {
        return repository.findById(id).orElse(null);
    }
}