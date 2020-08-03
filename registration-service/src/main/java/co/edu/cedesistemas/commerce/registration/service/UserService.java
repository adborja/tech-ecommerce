package co.edu.cedesistemas.commerce.registration.service;

import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.commerce.registration.repository.UserRepository;
import co.edu.cedesistemas.common.event.RegistrationEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserService{
    private final UserRepository repository;
    private final EventPublisherService publisherService;

    public User createUser(final User user) {
        user.setStatus(User.Status.ACTIVE);
        User created = repository.save(user);
        publisherService.publishRegistrationEvent(created, RegistrationEvent.Status.USER_CREATED);
        return created;
    }


    public User getById(final String id) {
        return repository.findById(id).orElse(null);
    }

    public User activeUser(String id) {
        User user = repository.findById(id).get();
        if (user != null){
            user.setStatus(User.Status.ACTIVE);
            user.setUpdatedAt(LocalDateTime.now());
            return repository.save(user);
        }else{
            return null;
        }
    }
    
        public void deleteUser(final String id) {
        User found = getById(id);
        if (found != null) {
            repository.deleteById(id);
            publisherService.publishRegistrationEvent(found, RegistrationEvent.Status.USER_DELETED);
        }
    }
}
