package co.edu.cedesistemas.commerce.registration.service;

import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.commerce.registration.repository.UserRepository;
import co.edu.cedesistemas.common.event.RegistrationEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private final EventPublisherService publisherService;

    public User findById(String id) {
        return this.userRepository.findById(id).orElse(null);
    }

    public User createUser(User user) {
        user.setStatus(User.Status.ACTIVE);
        User userCreated = this.userRepository.save(user);
        this.publisherService.publishRegistrationEvent(userCreated, RegistrationEvent.Status.USER_CREATED);
        return userCreated;
    }

    public User updateUser(User user) {
        return this.userRepository.save(user);
    }

    public void deleteUser(String id) {
        User foundUser = findById(id);

        if (foundUser != null) {
            this.userRepository.delete(foundUser);
            this.publisherService.publishRegistrationEvent(foundUser, RegistrationEvent.Status.USER_DELETED);
        }
    }
}
