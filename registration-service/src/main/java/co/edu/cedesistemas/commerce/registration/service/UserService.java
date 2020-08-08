package co.edu.cedesistemas.commerce.registration.service;


import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.commerce.registration.repository.UserRepository;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.event.RegistrationEvent;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Profile("!" + SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
@Slf4j
public class UserService implements IUserService {
    private final UserRepository repository;
    private final EventPublisherService publisherService;

    public User createUser(final User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setId(UUID.randomUUID().toString());
        user.setStatus(User.Status.ACTIVE);
        log.info("user created!! {} ",user);
        User created = repository.save(user);
        publisherService.publishRegistrationEvent(user, RegistrationEvent.Status.USER_CREATED);
        return created;
    }

    @Cacheable(value = "get-user-id", key = "#id")
    public User getById(final String id) {
        log.info("user found{}",id);
        try {
            log.info("simulating long time operation");
            Thread.sleep(3000L);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return repository.findById(id).orElse(null);
    }

    public void deleteUser(final String id) {
        User found = getById(id);
        if(found!=null){
            repository.deleteById(id);
            publisherService.publishRegistrationEvent(found, RegistrationEvent.Status.USER_DELETED);
        }
    }

    public List<User> getByEmail(final String email) {
        return repository.findByEmail(email);

    }
    public User updateUser(String id, User user) {
        User userToUpdate = repository.findById(id).get();
        BeanUtils.copyProperties(user, userToUpdate, Utils.getNullPropertyNames(user));
        return repository.save(userToUpdate);
    }
    public User activeUser(String id) {
        User user = repository.findById(id).orElse(null);
        if (user != null){
            user.setStatus(User.Status.ACTIVE);
            return repository.save(user);
        }else{
            return null;
        }
    }

}
