package co.edu.cedesistemas.commerce.registration.services;

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
    private final UserRepository userRepository;
    private final EventPublisherService publisherService;

    public User createUser(User user) {
        user.setStatus(User.Status.INACTIVE);
        User usercreated = userRepository.save(user);
        publisherService.publishRegistrationEvent(usercreated, RegistrationEvent.Status.USER_CREATED);
        return usercreated;
    }

    public User activateUser(String userId){
        return userRepository.findById(userId)
                .map(user -> {
                    user.setStatus(User.Status.ACTIVE);
                    return userRepository.save(user);
                }).orElse(null);

    }

    public User getUserById(String userId){

        return userRepository.findById(userId).orElse(null);
    }

    public void deleteUser(String userId){
        User userFound = getUserById(userId);
        if(userFound != null){
            userRepository.deleteById(userId);
            publisherService.publishRegistrationEvent(userFound, RegistrationEvent.Status.USER_DELETED);
        }

    }
}
