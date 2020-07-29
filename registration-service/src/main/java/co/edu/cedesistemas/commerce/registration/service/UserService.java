package co.edu.cedesistemas.commerce.registration.service;

import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.commerce.registration.repository.UserRepository;
import co.edu.cedesistemas.common.event.RegistrationEvent;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository repository;
    private final EventPublisherService publisherService;

    public User createUser(final User user) {
        log.info("R creating User {}", user.getName());
        user.setId(UUID.randomUUID().toString());
        user.setStatus(User.Status.INACTIVE);
        User created = repository.save(user);
        publisherService.publishRegistrationEvent(created, RegistrationEvent.Status.USER_CREATED);
        return created;
    }

    public User getById(final String id) {
        log.info("R retrieving Product by Id {}", id);
        return repository.findById(id).get();
    }

    public User updateUser(String id) {
        User user = getById(id);
        if(user == null){
            log.warn("R User not found: {}", id);
        }
        user.setStatus(User.Status.ACTIVE);
        return repository.save(user);
    }
    
    public void deleteUser(String id){
        User found = getById(id);
        if(found != null){
            repository.deleteById(id);
            publisherService.publishRegistrationEvent(found, RegistrationEvent.Status.USER_DELETED);
        }

    }
}
