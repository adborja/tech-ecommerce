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
        User found = getUserById(id);
        if (found != null) {
            repository.deleteById(id);
            publisherService.publishRegistrationEvent(found, RegistrationEvent.Status.USER_DELETED);
        }
    }

    public User getUserById(String id){
        return repository.findUserById(id).orElse(null);
    }

    public User activateUserById(String id){
        User user = repository.findUserById(id).orElse(new User());
        if (user.getId() != null) {
            user.setStatus(User.Status.ACTIVE);
            user = repository.save(user);
        }
        return user;
    }
}
