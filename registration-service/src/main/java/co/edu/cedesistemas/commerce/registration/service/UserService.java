package co.edu.cedesistemas.commerce.registration.service;

import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.commerce.registration.repository.UserRepository;
import co.edu.cedesistemas.common.event.RegistrationEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository repository;
    private final EventPublisherService publisherService;

    public User createUser(final User user) {
        user.setStatus(User.Status.ACTIVE);
        User created = repository.save(user);
        publisherService.publishRegistrationEvent(created, RegistrationEvent.Status.USER_CREATED);
        return created;
    }

    public void deleteUser(final String id) {
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
