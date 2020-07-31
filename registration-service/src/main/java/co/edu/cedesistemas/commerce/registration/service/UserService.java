package co.edu.cedesistemas.commerce.registration.service;

import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.commerce.registration.repository.UserRepository;
import co.edu.cedesistemas.common.event.RegistrationEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository repository;
    private final EventPublisherService publisherService;

    public User createUser(User user) {
        log.info("creating user {}", user.getName());
    	user.setStatus(User.Status.INACTIVE);
        User userCreated = repository.save(user);
    	publisherService.publishregistrationEvent(userCreated, RegistrationEvent.Status.USER_CREATED);
        return userCreated;
    }

    @Cacheable(value = "registration_service_user", key = "#id")
    public User getById(String id) {
        log.info("getting user by id: {}", id);

        try {
            log.info("simulating long time operation");
            Thread.sleep(5000L);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        return repository.findById(id).orElse(null);
    }

    public User activateUser(String id) {
        log.info("activate user with id: {}", id);
        User userActive = getById(id);
        userActive.setStatus(User.Status.ACTIVE);

        return repository.save(userActive);

    }

    public void deleteUser(String id){

        User found = getById(id);
        if (found != null){
            repository.deleteById(id);
            publisherService.publishregistrationEvent(found, RegistrationEvent.Status.USER_CREATED);
        }

    }
}
