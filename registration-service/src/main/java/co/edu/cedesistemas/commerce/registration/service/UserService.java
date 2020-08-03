package co.edu.cedesistemas.commerce.registration.service;

import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.commerce.registration.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService{
    private final UserRepository repository;

    public User createUser(final User user) {
        user.setId(UUID.randomUUID().toString());
        user.setStatus(User.Status.INACTIVE);
        user.setCreatedAt(LocalDateTime.now());
        return repository.save(user);
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
